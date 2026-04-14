<template>
  <div class="worker-orders-page">
    <div class="page-header">
      <h2>我的订单</h2>
    </div>
    
    <div class="filter-bar">
      <el-radio-group v-model="statusFilter" @change="loadOrders">
        <el-radio-button :value="null">全部</el-radio-button>
        <el-radio-button :value="1">待接单</el-radio-button>
        <el-radio-button :value="2">已接单</el-radio-button>
        <el-radio-button :value="3">进行中</el-radio-button>
        <el-radio-button :value="5">已完成</el-radio-button>
      </el-radio-group>
    </div>
    
    <el-table :data="orders" stripe v-loading="loading">
      <el-table-column prop="orderNo" label="订单号" width="180" />
      <el-table-column prop="categoryName" label="服务类型" width="120" />
      <el-table-column prop="contactName" label="联系人" width="100" />
      <el-table-column prop="contactPhone" label="联系电话" width="130" />
      <el-table-column prop="address" label="服务地址" />
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
      <el-table-column label="操作" fixed="right" width="180">
        <template #default="{ row }">
          <template v-if="row.status === 1">
            <el-button type="success" size="small" @click="handleAccept(row)">接单</el-button>
            <el-button type="danger" size="small" @click="handleReject(row)">拒单</el-button>
          </template>
          <template v-if="row.status === 2">
            <el-button type="primary" size="small" @click="handleStart(row)">开始服务</el-button>
          </template>
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getWorkerOrders, acceptOrder, rejectOrder, startOrder } from '@/api/order'

const orders = ref([])
const total = ref(0)
const current = ref(1)
const statusFilter = ref(null)
const loading = ref(false)

onMounted(() => {
  loadOrders()
})

const loadOrders = async () => {
  loading.value = true
  try {
    const res = await getWorkerOrders({
      current: current.value,
      size: 10,
      status: statusFilter.value
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
    1: 'warning',
    2: 'primary',
    3: 'primary',
    5: 'success'
  }
  return types[status] || 'info'
}

const handleAccept = async (order) => {
  try {
    await ElMessageBox.confirm('确定要接单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'success'
    })
    await acceptOrder(order.id)
    ElMessage.success('接单成功')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('接单失败', error)
    }
  }
}

const handleReject = async (order) => {
  try {
    const { value } = await ElMessageBox.prompt('请输入拒单原因', '拒单', {
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })
    await rejectOrder(order.id, value)
    ElMessage.success('已拒单')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('拒单失败', error)
    }
  }
}

const handleStart = async (order) => {
  try {
    await ElMessageBox.confirm('确定开始服务吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'success'
    })
    await startOrder(order.id)
    ElMessage.success('已开始服务')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('操作失败', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.worker-orders-page {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  
  .page-header {
    margin-bottom: $spacing-lg;
    
    h2 {
      font-size: 1.2rem;
      font-weight: 600;
      color: $text-primary;
    }
  }
  
  .filter-bar {
    margin-bottom: $spacing-lg;
  }
  
  .pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: $spacing-lg;
  }
}
</style>
