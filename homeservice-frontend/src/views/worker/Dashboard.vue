<template>
  <div class="worker-dashboard">
    <div class="welcome-section">
      <h2>欢迎回来，{{ userStore.user?.nickname }}</h2>
      <p>今天是个好日子，开始工作吧！</p>
    </div>
    
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon pending">
          <el-icon><Clock /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">{{ stats.pending }}</span>
          <span class="stat-label">待处理订单</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon progress">
          <el-icon><Loading /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">{{ stats.inProgress }}</span>
          <span class="stat-label">进行中订单</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon completed">
          <el-icon><Check /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">{{ stats.completed }}</span>
          <span class="stat-label">已完成订单</span>
        </div>
      </div>
    </div>
    
    <div class="recent-orders">
      <div class="section-header">
        <h3>待处理订单</h3>
        <router-link to="/worker/orders">查看全部</router-link>
      </div>
      
      <el-table :data="pendingOrders" stripe>
        <el-table-column prop="orderNo" label="订单号" width="180" />
        <el-table-column prop="categoryName" label="服务类型" width="120" />
        <el-table-column prop="address" label="服务地址" />
        <el-table-column prop="appointmentTime" label="预约时间" width="180" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="success" size="small" @click="handleAccept(row)">接单</el-button>
            <el-button type="danger" size="small" @click="handleReject(row)">拒单</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { getWorkerOrders, acceptOrder, rejectOrder } from '@/api/order'

const userStore = useUserStore()

const stats = reactive({
  pending: 0,
  inProgress: 0,
  completed: 0
})

const pendingOrders = ref([])

onMounted(async () => {
  await loadStats()
  await loadPendingOrders()
})

const loadStats = async () => {
  try {
    const [pendingRes, progressRes, completedRes] = await Promise.all([
      getWorkerOrders({ current: 1, size: 1, status: 1 }),
      getWorkerOrders({ current: 1, size: 1, status: 3 }),
      getWorkerOrders({ current: 1, size: 1, status: 5 })
    ])
    stats.pending = pendingRes.data.total
    stats.inProgress = progressRes.data.total
    stats.completed = completedRes.data.total
  } catch (error) {
    console.error('获取统计数据失败', error)
  }
}

const loadPendingOrders = async () => {
  try {
    const res = await getWorkerOrders({ current: 1, size: 5, status: 1 })
    pendingOrders.value = res.data.records
  } catch (error) {
    console.error('获取待处理订单失败', error)
  }
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
    loadStats()
    loadPendingOrders()
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
    loadPendingOrders()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('拒单失败', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.worker-dashboard {
  
}

.welcome-section {
  margin-bottom: $spacing-xl;
  
  h2 {
    font-family: $font-display;
    font-size: 1.8rem;
    font-weight: 600;
    color: $text-primary;
    margin-bottom: $spacing-xs;
  }
  
  p {
    color: $text-secondary;
  }
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: $spacing-lg;
  margin-bottom: $spacing-xl;
}

.stat-card {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  display: flex;
  align-items: center;
  gap: $spacing-md;
  box-shadow: $shadow-sm;
  
  .stat-icon {
    width: 60px;
    height: 60px;
    border-radius: $radius-md;
    display: flex;
    align-items: center;
    justify-content: center;
    
    .el-icon {
      font-size: 1.8rem;
      color: white;
    }
    
    &.pending {
      background: linear-gradient(135deg, #F39C12, #E67E22);
    }
    
    &.progress {
      background: linear-gradient(135deg, #3498DB, #2980B9);
    }
    
    &.completed {
      background: linear-gradient(135deg, #27AE60, #229954);
    }
  }
  
  .stat-info {
    .stat-value {
      display: block;
      font-size: 1.8rem;
      font-weight: 700;
      color: $text-primary;
    }
    
    .stat-label {
      font-size: 0.9rem;
      color: $text-secondary;
    }
  }
}

.recent-orders {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  
  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-md;
    
    h3 {
      font-size: 1.1rem;
      font-weight: 600;
      color: $text-primary;
    }
    
    a {
      font-size: 0.9rem;
      color: #27AE60;
      
      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
