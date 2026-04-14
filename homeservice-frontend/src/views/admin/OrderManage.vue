<template>
  <div class="order-manage-page">
    <div class="page-header">
      <h2>订单管理</h2>
      <div class="filter-group">
        <el-select v-model="statusFilter" placeholder="订单状态" clearable @change="loadOrders">
          <el-option label="待派单" :value="0" />
          <el-option label="已派单" :value="1" />
          <el-option label="已接单" :value="2" />
          <el-option label="进行中" :value="3" />
          <el-option label="待评价" :value="4" />
          <el-option label="已完成" :value="5" />
          <el-option label="已取消" :value="6" />
        </el-select>
        <el-input
          v-model="keyword"
          placeholder="搜索订单"
          style="width: 200px"
          clearable
          @clear="loadOrders"
          @keyup.enter="loadOrders"
        />
      </div>
    </div>
    
    <el-table :data="orders" stripe v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" width="180" />
      <el-table-column prop="categoryName" label="服务类型" width="120" />
      <el-table-column prop="contactName" label="联系人" width="100" />
      <el-table-column prop="contactPhone" label="联系电话" width="130" />
      <el-table-column prop="totalPrice" label="金额" width="100">
        <template #default="{ row }">
          ¥{{ row.totalPrice }}
        </template>
      </el-table-column>
      <el-table-column prop="statusDesc" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)" size="small">{{ row.statusDesc }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <el-button
            v-if="row.status === 0"
            type="primary"
            size="small"
            link
            @click="handleAssign(row)"
          >
            派单
          </el-button>
          <el-button
            v-if="row.status === 0 || row.status === 1"
            type="danger"
            size="small"
            link
            @click="handleException(row)"
          >
            取消
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="pagination">
      <el-pagination
        v-model:current-page="current"
        :page-size="10"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadOrders"
      />
    </div>
    
    <el-dialog v-model="assignDialogVisible" title="派单" width="500px">
      <el-form label-width="80px">
        <el-form-item label="选择人员">
          <el-select v-model="selectedWorkerId" placeholder="请选择服务人员" style="width: 100%">
            <el-option
              v-for="worker in workers"
              :key="worker.id"
              :label="worker.realName"
              :value="worker.id"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="assignDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmAssign">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAdminOrders, assignOrder, handleOrderException } from '@/api/order'

const orders = ref([])
const total = ref(0)
const current = ref(1)
const statusFilter = ref(null)
const keyword = ref('')
const loading = ref(false)

const assignDialogVisible = ref(false)
const selectedWorkerId = ref(null)
const currentOrder = ref(null)
const workers = ref([])

onMounted(() => {
  loadOrders()
})

const loadOrders = async () => {
  loading.value = true
  try {
    const res = await getAdminOrders({
      current: current.value,
      size: 10,
      status: statusFilter.value,
      keyword: keyword.value
    })
    orders.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取订单列表失败', error)
  } finally {
    loading.value = false
  }
}

const getStatusType = (status) => {
  const types = {
    0: 'info',
    1: 'warning',
    2: 'primary',
    3: 'primary',
    4: 'success',
    5: 'success',
    6: 'danger',
    7: 'danger'
  }
  return types[status] || 'info'
}

const handleAssign = (order) => {
  currentOrder.value = order
  selectedWorkerId.value = null
  assignDialogVisible.value = true
}

const confirmAssign = async () => {
  if (!selectedWorkerId.value) {
    ElMessage.warning('请选择服务人员')
    return
  }
  
  try {
    await assignOrder(currentOrder.value.id, selectedWorkerId.value)
    ElMessage.success('派单成功')
    assignDialogVisible.value = false
    loadOrders()
  } catch (error) {
    console.error('派单失败', error)
  }
}

const handleException = async (order) => {
  try {
    const { value } = await ElMessageBox.prompt('请输入取消原因', '取消订单', {
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })
    await handleOrderException(order.id, value)
    ElMessage.success('订单已取消')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消订单失败', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.order-manage-page {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-lg;
    
    h2 {
      font-size: 1.2rem;
      font-weight: 600;
      color: $text-primary;
    }
    
    .filter-group {
      display: flex;
      gap: $spacing-md;
    }
  }
  
  .pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: $spacing-lg;
  }
}
</style>
