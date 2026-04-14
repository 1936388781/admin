<template>
  <div class="service-list-page">
    <div class="page-header">
      <h1>服务列表</h1>
      <p>选择您需要的服务，我们为您提供专业的家政服务</p>
    </div>
    
    <div class="services-grid">
      <div 
        v-for="service in services" 
        :key="service.id" 
        class="service-card"
        @click="goToDetail(service.id)"
      >
        <div class="service-icon">
          <component :is="getServiceIcon(service.name)" />
        </div>
        <div class="service-info">
          <h3>{{ service.name }}</h3>
          <p>{{ service.description }}</p>
          <div class="service-footer">
            <span class="price">¥{{ service.price }}<small>/次</small></span>
            <el-button type="primary" size="small">立即预约</el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategories } from '@/api/service'

const router = useRouter()
const services = ref([])

onMounted(async () => {
  try {
    const res = await getCategories()
    services.value = res.data
  } catch (error) {
    console.error('获取服务列表失败', error)
  }
})

const goToDetail = (id) => {
  router.push(`/service/${id}`)
}

const getServiceIcon = (name) => {
  const iconMap = {
    '日常保洁': 'Brush',
    '深度保洁': 'Clean',
    '月嫂服务': 'User',
    '保姆服务': 'UserFilled',
    '家电清洗': 'Monitor',
    '搬家服务': 'Van',
    '管道疏通': 'Connection',
    '除甲醛': 'WindPower'
  }
  return iconMap[name] || 'Grid'
}
</script>

<style lang="scss" scoped>
.service-list-page {
  padding: $spacing-xxl $spacing-lg;
  max-width: 1400px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: $spacing-xxl;
  
  h1 {
    font-family: $font-display;
    font-size: 2.5rem;
    font-weight: 600;
    margin-bottom: $spacing-md;
    color: $text-primary;
  }
  
  p {
    font-size: 1.1rem;
    color: $text-secondary;
  }
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: $spacing-lg;
  
  @media (max-width: 1000px) {
    grid-template-columns: repeat(2, 1fr);
  }
  
  @media (max-width: 600px) {
    grid-template-columns: 1fr;
  }
}

.service-card {
  background: white;
  border-radius: $radius-lg;
  overflow: hidden;
  cursor: pointer;
  transition: all $transition-normal;
  border: 1px solid rgba(0, 0, 0, 0.05);
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: $shadow-card;
  }
  
  .service-icon {
    height: 120px;
    background: linear-gradient(135deg, rgba($primary-color, 0.1), rgba($primary-light, 0.1));
    display: flex;
    align-items: center;
    justify-content: center;
    
    .el-icon {
      font-size: 3rem;
      color: $primary-color;
    }
  }
  
  .service-info {
    padding: $spacing-lg;
    
    h3 {
      font-size: 1.2rem;
      font-weight: 600;
      margin-bottom: $spacing-sm;
      color: $text-primary;
    }
    
    p {
      font-size: 0.9rem;
      color: $text-secondary;
      line-height: 1.6;
      margin-bottom: $spacing-md;
    }
  }
  
  .service-footer {
    display: flex;
    align-items: center;
    justify-content: space-between;
    
    .price {
      font-size: 1.5rem;
      font-weight: 700;
      color: $primary-color;
      
      small {
        font-size: 0.8rem;
        font-weight: 400;
        color: $text-light;
      }
    }
  }
}
</style>
