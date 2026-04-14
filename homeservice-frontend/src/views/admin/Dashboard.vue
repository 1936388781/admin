<template>
  <div class="admin-dashboard">
    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon users">
          <el-icon><User /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">{{ stats.totalUsers }}</span>
          <span class="stat-label">总用户数</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon orders">
          <el-icon><Document /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">{{ stats.totalOrders }}</span>
          <span class="stat-label">总订单数</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon revenue">
          <el-icon><Money /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">¥{{ stats.totalRevenue }}</span>
          <span class="stat-label">总营收</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon workers">
          <el-icon><Avatar /></el-icon>
        </div>
        <div class="stat-info">
          <span class="stat-value">0</span>
          <span class="stat-label">服务人员</span>
        </div>
      </div>
    </div>
    
    <div class="dashboard-content">
      <div class="recent-orders">
        <div class="section-header">
          <h3>最近订单</h3>
          <router-link to="/admin/orders">查看全部</router-link>
        </div>
        <el-table :data="recentOrders" stripe>
          <el-table-column prop="orderNo" label="订单号" width="180" />
          <el-table-column prop="categoryName" label="服务类型" width="120" />
          <el-table-column prop="contactName" label="联系人" width="100" />
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
          <el-table-column prop="createTime" label="创建时间" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getStatistics } from '@/api/admin'
import { getAdminOrders } from '@/api/order'

const stats = reactive({
  totalUsers: 0,
  totalOrders: 0,
  totalRevenue: 0
})

const recentOrders = ref([])

onMounted(async () => {
  try {
    const res = await getStatistics()
    stats.totalUsers = res.data.totalUsers
    stats.totalOrders = res.data.totalOrders
    stats.totalRevenue = res.data.totalRevenue
    
    const ordersRes = await getAdminOrders({ current: 1, size: 5 })
    recentOrders.value = ordersRes.data.records
  } catch (error) {
    console.error('获取统计数据失败', error)
  }
})

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
</script>

<style lang="scss" scoped>
.admin-dashboard {
  
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: $spacing-lg;
  margin-bottom: $spacing-xl;
  
  @media (max-width: 1200px) {
    grid-template-columns: repeat(2, 1fr);
  }
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
    
    &.users {
      background: linear-gradient(135deg, #3498DB, #2980B9);
    }
    
    &.orders {
      background: linear-gradient(135deg, #9B59B6, #8E44AD);
    }
    
    &.revenue {
      background: linear-gradient(135deg, #E67E22, #D35400);
    }
    
    &.workers {
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

.dashboard-content {
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
      color: $primary-color;
      
      &:hover {
        text-decoration: underline;
      }
    }
  }
}
</style>
