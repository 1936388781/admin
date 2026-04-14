<template>
  <div class="order-list-page">
    <div class="page-header">
      <h1>我的订单</h1>
    </div>
    
    <div class="filter-bar">
      <el-radio-group v-model="statusFilter" @change="loadOrders">
        <el-radio-button :value="null">全部</el-radio-button>
        <el-radio-button :value="0">待派单</el-radio-button>
        <el-radio-button :value="2">已接单</el-radio-button>
        <el-radio-button :value="3">进行中</el-radio-button>
        <el-radio-button :value="4">待评价</el-radio-button>
        <el-radio-button :value="5">已完成</el-radio-button>
      </el-radio-group>
    </div>
    
    <div class="orders-list">
      <div v-for="order in orders" :key="order.id" class="order-card">
        <div class="order-header">
          <span class="order-no">订单号：{{ order.orderNo }}</span>
          <el-tag :type="getStatusType(order.status)">{{ order.statusDesc }}</el-tag>
        </div>
        <div class="order-body">
          <div class="order-info">
            <div class="info-item">
              <span class="label">服务类型</span>
              <span class="value">{{ order.categoryName }}</span>
            </div>
            <div class="info-item">
              <span class="label">预约时间</span>
              <span class="value">{{ formatTime(order.appointmentTime) }}</span>
            </div>
            <div class="info-item">
              <span class="label">服务地址</span>
              <span class="value">{{ order.address }}</span>
            </div>
            <div class="info-item">
              <span class="label">订单金额</span>
              <span class="value price">¥{{ order.totalPrice }}</span>
            </div>
          </div>
        </div>
        <div class="order-footer">
          <el-button size="small" @click="viewDetail(order.id)">查看详情</el-button>
          <el-button 
            v-if="order.status === 0 || order.status === 1"
            size="small" 
            type="danger"
            @click="handleCancel(order)"
          >
            取消订单
          </el-button>
          <el-button 
            v-if="order.status === 3"
            size="small" 
            type="success"
            @click="handleConfirm(order)"
          >
            确认完成
          </el-button>
        </div>
      </div>
      
      <el-empty v-if="orders.length === 0" description="暂无订单" />
    </div>
    
    <div class="pagination" v-if="total > 0">
      <el-pagination
        v-model:current-page="current"
        :page-size="10"
        :total="total"
        layout="prev, pager, next"
        @current-change="loadOrders"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMyOrders, cancelOrder, confirmOrder } from '@/api/order'
import dayjs from 'dayjs'

const router = useRouter()

const orders = ref([])
const total = ref(0)
const current = ref(1)
const statusFilter = ref(null)

onMounted(() => {
  loadOrders()
})

const loadOrders = async () => {
  try {
    const res = await getMyOrders({
      current: current.value,
      size: 10,
      status: statusFilter.value
    })
    orders.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取订单列表失败', error)
  }
}

const formatTime = (time) => {
  return dayjs(time).format('YYYY-MM-DD HH:mm')
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

const viewDetail = (id) => {
  router.push(`/order/${id}`)
}

const handleCancel = async (order) => {
  try {
    const { value } = await ElMessageBox.prompt('请输入取消原因', '取消订单', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPlaceholder: '请输入取消原因'
    })
    await cancelOrder(order.id, value)
    ElMessage.success('订单已取消')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消订单失败', error)
    }
  }
}

const handleConfirm = async (order) => {
  try {
    await ElMessageBox.confirm('确认服务已完成？', '确认完成', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'success'
    })
    await confirmOrder(order.id)
    ElMessage.success('已确认完成')
    loadOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('确认完成失败', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.order-list-page {
  padding: $spacing-xxl $spacing-lg;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: $spacing-xl;
  
  h1 {
    font-family: $font-display;
    font-size: 2rem;
    font-weight: 600;
    color: $text-primary;
  }
}

.filter-bar {
  margin-bottom: $spacing-lg;
}

.orders-list {
  display: flex;
  flex-direction: column;
  gap: $spacing-md;
}

.order-card {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  
  .order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: $spacing-md;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    margin-bottom: $spacing-md;
    
    .order-no {
      font-size: 0.9rem;
      color: $text-secondary;
    }
  }
  
  .order-info {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: $spacing-md;
    
    @media (max-width: 800px) {
      grid-template-columns: repeat(2, 1fr);
    }
    
    .info-item {
      .label {
        display: block;
        font-size: 0.85rem;
        color: $text-light;
        margin-bottom: $spacing-xs;
      }
      
      .value {
        font-size: 0.95rem;
        color: $text-primary;
        
        &.price {
          font-weight: 600;
          color: $primary-color;
        }
      }
    }
  }
  
  .order-footer {
    display: flex;
    justify-content: flex-end;
    gap: $spacing-sm;
    padding-top: $spacing-md;
    margin-top: $spacing-md;
    border-top: 1px solid rgba(0, 0, 0, 0.05);
  }
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: $spacing-xl;
}
</style>
