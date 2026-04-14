<template>
  <div class="home-page">
    <section class="hero">
      <div class="hero-content">
        <h1 class="hero-title">
          <span class="title-line">专业家政服务</span>
          <span class="title-line highlight">让生活更美好</span>
        </h1>
        <p class="hero-desc">
          我们提供专业的家政服务，包括保洁、月嫂、保姆等多种服务类型，
          让您的家庭生活更加轻松舒适。
        </p>
        <div class="hero-actions">
          <router-link to="/services" class="btn-primary">
            立即预约
            <el-icon><ArrowRight /></el-icon>
          </router-link>
          <a href="#services" class="btn-secondary">了解更多</a>
        </div>
      </div>
      <div class="hero-image">
        <div class="floating-card card-1">
          <el-icon><Check /></el-icon>
          <span>专业认证</span>
        </div>
        <div class="floating-card card-2">
          <el-icon><Timer /></el-icon>
          <span>准时服务</span>
        </div>
        <div class="floating-card card-3">
          <el-icon><Star /></el-icon>
          <span>好评如潮</span>
        </div>
      </div>
    </section>
    
    <section id="services" class="services-section">
      <div class="section-container">
        <h2 class="section-title">热门服务</h2>
        <div class="services-grid">
          <div 
            v-for="service in services" 
            :key="service.id" 
            class="service-card"
            @click="goToService(service.id)"
          >
            <div class="service-icon">
              <component :is="getServiceIcon(service.name)" />
            </div>
            <h3 class="service-name">{{ service.name }}</h3>
            <p class="service-desc">{{ service.description }}</p>
            <div class="service-price">
              <span class="price-label">起</span>
              <span class="price-value">¥{{ service.price }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    <section class="features-section">
      <div class="section-container">
        <h2 class="section-title">为什么选择我们</h2>
        <div class="features-grid">
          <div class="feature-item">
            <div class="feature-icon">
              <el-icon><ShieldCheck /></el-icon>
            </div>
            <h3>安全保障</h3>
            <p>所有服务人员经过严格背景调查，确保您的家庭安全</p>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <el-icon><Medal /></el-icon>
            </div>
            <h3>专业培训</h3>
            <p>服务人员均经过专业培训，持证上岗</p>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <el-icon><Clock /></el-icon>
            </div>
            <h3>准时服务</h3>
            <p>准时上门服务，不耽误您的宝贵时间</p>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <el-icon><Service /></el-icon>
            </div>
            <h3>贴心售后</h3>
            <p>完善的售后服务体系，让您无后顾之忧</p>
          </div>
        </div>
      </div>
    </section>
    
    <section class="stats-section">
      <div class="section-container">
        <div class="stats-grid">
          <div class="stat-item">
            <div class="stat-value">10,000+</div>
            <div class="stat-label">服务家庭</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">500+</div>
            <div class="stat-label">专业服务人员</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">98%</div>
            <div class="stat-label">好评率</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">24h</div>
            <div class="stat-label">快速响应</div>
          </div>
        </div>
      </div>
    </section>
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
    services.value = res.data.slice(0, 8)
  } catch (error) {
    console.error('获取服务列表失败', error)
  }
})

const goToService = (id) => {
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
.home-page {
  overflow-x: hidden;
}

.hero {
  min-height: calc(100vh - 70px);
  display: flex;
  align-items: center;
  padding: $spacing-xxl $spacing-lg;
  background: linear-gradient(135deg, #FFF9F5 0%, #FFF 50%, #F5F9FF 100%);
  position: relative;
  
  .hero-content {
    flex: 1;
    max-width: 600px;
    padding-left: 100px;
  }
  
  .hero-title {
    font-family: $font-display;
    font-size: 3.5rem;
    font-weight: 700;
    line-height: 1.2;
    margin-bottom: $spacing-lg;
    
    .title-line {
      display: block;
    }
    
    .highlight {
      background: linear-gradient(90deg, $primary-color, $primary-light);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
  }
  
  .hero-desc {
    font-size: 1.2rem;
    color: $text-secondary;
    line-height: 1.8;
    margin-bottom: $spacing-xl;
  }
  
  .hero-actions {
    display: flex;
    gap: $spacing-md;
  }
  
  .btn-primary {
    display: inline-flex;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-md $spacing-xl;
    background: linear-gradient(135deg, $primary-color, $primary-light);
    color: white;
    border-radius: $radius-lg;
    font-weight: 600;
    font-size: 1.1rem;
    transition: all $transition-fast;
    
    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 8px 24px rgba($primary-color, 0.4);
    }
  }
  
  .btn-secondary {
    padding: $spacing-md $spacing-xl;
    border: 2px solid $text-light;
    border-radius: $radius-lg;
    font-weight: 600;
    color: $text-secondary;
    transition: all $transition-fast;
    
    &:hover {
      border-color: $primary-color;
      color: $primary-color;
    }
  }
  
  .hero-image {
    flex: 1;
    position: relative;
    height: 500px;
  }
  
  .floating-card {
    position: absolute;
    background: white;
    padding: $spacing-md $spacing-lg;
    border-radius: $radius-lg;
    box-shadow: $shadow-lg;
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    font-weight: 500;
    animation: float 3s ease-in-out infinite;
    
    .el-icon {
      font-size: 1.5rem;
      color: $primary-color;
    }
    
    &.card-1 {
      top: 20%;
      left: 10%;
      animation-delay: 0s;
    }
    
    &.card-2 {
      top: 50%;
      right: 15%;
      animation-delay: 1s;
    }
    
    &.card-3 {
      bottom: 15%;
      left: 25%;
      animation-delay: 2s;
    }
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-15px);
  }
}

.services-section {
  padding: 100px $spacing-lg;
  background: white;
  
  .section-container {
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .services-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: $spacing-lg;
    
    @media (max-width: 1200px) {
      grid-template-columns: repeat(3, 1fr);
    }
    
    @media (max-width: 900px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .service-card {
    background: white;
    border-radius: $radius-lg;
    padding: $spacing-xl;
    text-align: center;
    cursor: pointer;
    transition: all $transition-normal;
    border: 1px solid rgba(0, 0, 0, 0.05);
    
    &:hover {
      transform: translateY(-8px);
      box-shadow: $shadow-card;
      border-color: rgba($primary-color, 0.2);
      
      .service-icon {
        background: linear-gradient(135deg, $primary-color, $primary-light);
        
        .el-icon {
          color: white;
        }
      }
    }
    
    .service-icon {
      width: 80px;
      height: 80px;
      margin: 0 auto $spacing-md;
      background: rgba($primary-color, 0.1);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all $transition-normal;
      
      .el-icon {
        font-size: 2rem;
        color: $primary-color;
        transition: color $transition-normal;
      }
    }
    
    .service-name {
      font-size: 1.2rem;
      font-weight: 600;
      margin-bottom: $spacing-sm;
      color: $text-primary;
    }
    
    .service-desc {
      font-size: 0.9rem;
      color: $text-secondary;
      margin-bottom: $spacing-md;
      line-height: 1.6;
    }
    
    .service-price {
      .price-label {
        font-size: 0.85rem;
        color: $text-light;
      }
      
      .price-value {
        font-size: 1.5rem;
        font-weight: 700;
        color: $primary-color;
      }
    }
  }
}

.features-section {
  padding: 100px $spacing-lg;
  background: linear-gradient(180deg, #FAFAFA 0%, #FFF 100%);
  
  .section-container {
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .features-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: $spacing-xl;
    
    @media (max-width: 1000px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .feature-item {
    text-align: center;
    padding: $spacing-lg;
    
    .feature-icon {
      width: 70px;
      height: 70px;
      margin: 0 auto $spacing-md;
      background: linear-gradient(135deg, $primary-color, $primary-light);
      border-radius: $radius-lg;
      display: flex;
      align-items: center;
      justify-content: center;
      
      .el-icon {
        font-size: 1.8rem;
        color: white;
      }
    }
    
    h3 {
      font-size: 1.1rem;
      font-weight: 600;
      margin-bottom: $spacing-sm;
      color: $text-primary;
    }
    
    p {
      font-size: 0.9rem;
      color: $text-secondary;
      line-height: 1.6;
    }
  }
}

.stats-section {
  padding: 80px $spacing-lg;
  background: linear-gradient(135deg, $secondary-color, $secondary-light);
  
  .section-container {
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .stats-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: $spacing-xl;
    
    @media (max-width: 800px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .stat-item {
    text-align: center;
    
    .stat-value {
      font-family: $font-display;
      font-size: 3rem;
      font-weight: 700;
      color: white;
      margin-bottom: $spacing-xs;
    }
    
    .stat-label {
      font-size: 1rem;
      color: rgba(255, 255, 255, 0.8);
    }
  }
}
</style>
