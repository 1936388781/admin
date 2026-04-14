<template>
  <div class="order-detail-page">
    <div class="page-container" v-if="order">
      <div class="detail-card">
        <div class="card-header">
          <h2>订单详情</h2>
          <el-tag :type="getStatusType(order.status)" size="large">{{ order.statusDesc }}</el-tag>
        </div>
        
        <div class="detail-section">
          <h3>订单信息</h3>
          <div class="info-grid">
            <div class="info-item">
              <span class="label">订单编号</span>
              <span class="value">{{ order.orderNo }}</span>
            </div>
            <div class="info-item">
              <span class="label">服务类型</span>
              <span class="value">{{ order.categoryName }}</span>
            </div>
            <div class="info-item">
              <span class="label">预约时间</span>
              <span class="value">{{ formatTime(order.appointmentTime) }}</span>
            </div>
            <div class="info-item">
              <span class="label">订单金额</span>
              <span class="value price">¥{{ order.totalPrice }}</span>
            </div>
          </div>
        </div>
        
        <div class="detail-section">
          <h3>联系信息</h3>
          <div class="info-grid">
            <div class="info-item">
              <span class="label">联系人</span>
              <span class="value">{{ order.contactName }}</span>
            </div>
            <div class="info-item">
              <span class="label">联系电话</span>
              <span class="value">{{ order.contactPhone }}</span>
            </div>
            <div class="info-item">
              <span class="label">服务地址</span>
              <span class="value">{{ order.address }}</span>
            </div>
          </div>
        </div>
        
        <div class="detail-section" v-if="order.workerName">
          <h3>服务人员</h3>
          <div class="info-grid">
            <div class="info-item">
              <span class="label">服务人员</span>
              <span class="value">{{ order.workerName }}</span>
            </div>
          </div>
        </div>
        
        <div class="detail-section" v-if="order.remark">
          <h3>备注信息</h3>
          <p>{{ order.remark }}</p>
        </div>
      </div>
      
      <div class="action-bar">
        <el-button 
          v-if="order.status === 0 || order.status === 1"
          type="danger"
          @click="handleCancel"
        >
          取消订单
        </el-button>
        <el-button 
          v-if="order.status === 3"
          type="success"
          @click="handleConfirm"
        >
          确认完成
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getOrderDetail, cancelOrder, confirmOrder } from '@/api/order'
import dayjs from 'dayjs'

const route = useRoute()
const router = useRouter()

const order = ref(null)

onMounted(async () => {
  try {
    const res = await getOrderDetail(route.params.id)
    order.value = res.data
  } catch (error) {
    console.error('获取订单详情失败', error)
  }
})

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

const handleCancel = async () => {
  try {
    const { value } = await ElMessageBox.prompt('请输入取消原因', '取消订单', {
      confirmButtonText: '确定',
      cancelButtonText: '取消'
    })
    await cancelOrder(order.value.id, value)
    ElMessage.success('订单已取消')
    router.push('/orders')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消订单失败', error)
    }
  }
}

const handleConfirm = async () => {
  try {
    await ElMessageBox.confirm('确认服务已完成？', '确认完成', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'success'
    })
    await confirmOrder(order.value.id)
    ElMessage.success('已确认完成')
    router.push('/orders')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('确认完成失败', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.order-detail-page {
  padding: $spacing-xxl $spacing-lg;
  max-width: 900px;
  margin: 0 auto;
}

.detail-card {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-xl;
  box-shadow: $shadow-sm;
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-xl;
    padding-bottom: $spacing-lg;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    
    h2 {
      font-family: $font-display;
      font-size: 1.5rem;
      font-weight: 600;
      color: $text-primary;
    }
  }
}

.detail-section {
  margin-bottom: $spacing-xl;
  
  h3 {
    font-size: 1.1rem;
    font-weight: 600;
    color: $text-primary;
    margin-bottom: $spacing-md;
    padding-left: $spacing-sm;
    border-left: 3px solid $primary-color;
  }
  
  p {
    color: $text-secondary;
    line-height: 1.6;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: $spacing-md;
  
  .info-item {
    .label {
      display: block;
      font-size: 0.85rem;
      color: $text-light;
      margin-bottom: $spacing-xs;
    }
    
    .value {
      font-size: 1rem;
      color: $text-primary;
      
      &.price {
        font-size: 1.2rem;
        font-weight: 600;
        color: $primary-color;
      }
    }
  }
}

.action-bar {
  display: flex;
  justify-content: flex-end;
  gap: $spacing-md;
  margin-top: $spacing-xl;
}
</style>
