<template>
  <div class="home-page">
    <section class="hero">
      <div class="hero-bg-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
      </div>
      <div class="hero-container">
        <div class="hero-content">
          <div class="hero-badge">
            <span class="badge-dot"></span>
            值得信赖的家政服务平台
          </div>
          <h1 class="hero-title">
            <span class="title-line">专业家政服务</span>
            <span class="title-line highlight">让家更温馨</span>
          </h1>
          <p class="hero-desc">
            汇聚超过500名专业服务人员，提供日常保洁、深度清洁、母婴护理等全方位家政服务，让您省心更放心。
          </p>
          <div class="hero-actions">
            <router-link to="/services" class="btn-primary">
              <span>立即预约</span>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M5 12h14M12 5l7 7-7 7"/>
              </svg>
            </router-link>
            <a href="#services" class="btn-secondary">
              <span>了解更多</span>
            </a>
          </div>
          <div class="hero-stats">
            <div class="stat">
              <span class="stat-number">10,000+</span>
              <span class="stat-label">服务家庭</span>
            </div>
            <div class="stat-divider"></div>
            <div class="stat">
              <span class="stat-number">500+</span>
              <span class="stat-label">专业技师</span>
            </div>
            <div class="stat-divider"></div>
            <div class="stat">
              <span class="stat-number">98%</span>
              <span class="stat-label">好评率</span>
            </div>
          </div>
        </div>
        <div class="hero-visual">
          <div class="hero-image-wrapper">
            <div class="main-image">
              <img src="https://images.unsplash.com/photo-1581578731548-c64695cc6952?w=600&h=500&fit=crop" alt="家政服务" />
            </div>
            <div class="floating-card card-1">
              <div class="card-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                  <polyline points="22 4 12 14.01 9 11.01"/>
                </svg>
              </div>
              <div class="card-text">
                <span class="card-title">专业认证</span>
                <span class="card-subtitle">持证上岗</span>
              </div>
            </div>
            <div class="floating-card card-2">
              <div class="card-icon star">
                <svg viewBox="0 0 24 24" fill="currentColor">
                  <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                </svg>
              </div>
              <div class="card-text">
                <span class="card-title">4.9</span>
                <span class="card-subtitle">用户评分</span>
              </div>
            </div>
            <div class="floating-card card-3">
              <div class="card-icon">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <circle cx="12" cy="12" r="10"/>
                  <polyline points="12 6 12 12 16 14"/>
                </svg>
              </div>
              <div class="card-text">
                <span class="card-title">24小时</span>
                <span class="card-subtitle">快速响应</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    
    <section id="services" class="services-section">
      <div class="section-container">
        <div class="section-header">
          <div class="section-label">热门服务</div>
          <h2 class="section-title">发现我们的专业服务</h2>
          <p class="section-desc">精心挑选的高品质服务，为您的家庭生活保驾护航</p>
        </div>
        <div class="services-grid">
          <div 
            v-for="(service, index) in services" 
            :key="service.id" 
            class="service-card"
            :style="{ animationDelay: `${index * 0.1}s` }"
            @click="goToService(service.id)"
          >
            <div class="card-image">
              <img :src="serviceImages[service.name] || defaultImage" :alt="service.name" />
              <div class="card-overlay"></div>
              <div class="card-tag">{{ serviceTags[service.name] || '热门' }}</div>
            </div>
            <div class="card-body">
              <div class="card-icon">
                <component :is="getServiceIcon(service.name)" />
              </div>
              <h3 class="service-name">{{ service.name }}</h3>
              <p class="service-desc">{{ service.description }}</p>
              <div class="service-footer">
                <div class="service-price">
                  <span class="price-currency">¥</span>
                  <span class="price-value">{{ service.price }}</span>
                  <span class="price-unit">/次</span>
                </div>
                <div class="service-btn">
                  预约
                  <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M5 12h14M12 5l7 7-7 7"/>
                  </svg>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="section-action">
          <router-link to="/services" class="view-all-btn">
            查看全部服务
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M5 12h14M12 5l7 7-7 7"/>
            </svg>
          </router-link>
        </div>
      </div>
    </section>
    
    <section class="features-section">
      <div class="features-bg-pattern"></div>
      <div class="section-container">
        <div class="section-header light">
          <div class="section-label">选择理由</div>
          <h2 class="section-title">为什么选择家家家政</h2>
          <p class="section-desc">专业、省心、安全 — 您家庭生活的贴心管家</p>
        </div>
        <div class="features-grid">
          <div class="feature-item">
            <div class="feature-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
                <polyline points="9 12 11 14 15 10"/>
              </svg>
            </div>
            <h3>安全保障</h3>
            <p>所有服务人员经过严格背景调查，专业培训持证上岗，让您的家庭安全无忧</p>
            <div class="feature-decoration"></div>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="8" r="5"/>
                <path d="M3 21v-2a7 7 0 0 1 7-7h4a7 7 0 0 1 7 7v2"/>
                <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
              </svg>
            </div>
            <h3>专业团队</h3>
            <p>汇聚500+专业服务人员，平均5年以上行业经验，服务更专业更贴心</p>
            <div class="feature-decoration"></div>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <circle cx="12" cy="12" r="10"/>
                <polyline points="12 6 12 12 16 14"/>
              </svg>
            </div>
            <h3>准时服务</h3>
            <p>严格遵守服务时间，准时上门不迟到，特殊情况提前沟通，灵活调整</p>
            <div class="feature-decoration"></div>
          </div>
          <div class="feature-item">
            <div class="feature-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
              </svg>
            </div>
            <h3>贴心售后</h3>
            <p>7×24小时客服在线，服务不满意可随时反馈，售后有保障更放心</p>
            <div class="feature-decoration"></div>
          </div>
        </div>
      </div>
    </section>
    
    <section class="cta-section">
      <div class="cta-container">
        <div class="cta-content">
          <h2>准备好让家变得更美好？</h2>
          <p>立即预约专业家政服务，体验品质生活的乐趣</p>
          <div class="cta-buttons">
            <router-link to="/services" class="cta-btn-primary">
              立即预约
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                <path d="M5 12h14M12 5l7 7-7 7"/>
              </svg>
            </router-link>
            <router-link to="/login" class="cta-btn-secondary">
              登录账号
            </router-link>
          </div>
        </div>
        <div class="cta-decoration">
          <div class="deco-circle"></div>
          <div class="deco-circle"></div>
          <div class="deco-circle"></div>
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

const defaultImage = 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=400&h=300&fit=crop'

const serviceImages = {
  '日常保洁': 'https://images.unsplash.com/photo-1581578731548-c64695cc6952?w=400&h=300&fit=crop',
  '深度保洁': 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=400&h=300&fit=crop',
  '月嫂服务': 'https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=400&h=300&fit=crop',
  '保姆服务': 'https://images.unsplash.com/photo-1559305616-3f99cd43e353?w=400&h=300&fit=crop',
  '家电清洗': 'https://images.unsplash.com/photo-1556909114-44e3e70034e2?w=400&h=300&fit=crop',
  '搬家服务': 'https://images.unsplash.com/photo-1600518464441-9154a4dea21b?w=400&h=300&fit=crop',
  '管道疏通': 'https://images.unsplash.com/photo-1504307651254-35680f356dfd?w=400&h=300&fit=crop',
  '除甲醛': 'https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?w=400&h=300&fit=crop'
}

const serviceTags = {
  '日常保洁': '热门',
  '深度保洁': '推荐',
  '月嫂服务': '金牌',
  '保姆服务': '优选',
  '家电清洗': '热销',
  '搬家服务': '专业',
  '管道疏通': '急修',
  '除甲醛': '环保'
}

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
  position: relative;
  min-height: calc(100vh - 70px);
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #FFF9F5 0%, #FFFFFF 40%, #F8FAFC 100%);
  overflow: hidden;
  padding: 60px 0;
  
  .hero-bg-shapes {
    position: absolute;
    inset: 0;
    overflow: hidden;
    
    .shape {
      position: absolute;
      border-radius: 50%;
      opacity: 0.5;
    }
    
    .shape-1 {
      width: 600px;
      height: 600px;
      background: radial-gradient(circle, rgba($primary-color, 0.08) 0%, transparent 70%);
      top: -200px;
      right: -100px;
    }
    
    .shape-2 {
      width: 400px;
      height: 400px;
      background: radial-gradient(circle, rgba($primary-light, 0.1) 0%, transparent 70%);
      bottom: -100px;
      left: -100px;
    }
    
    .shape-3 {
      width: 300px;
      height: 300px;
      background: radial-gradient(circle, rgba($secondary-color, 0.05) 0%, transparent 70%);
      top: 50%;
      left: 50%;
    }
  }
  
  .hero-container {
    position: relative;
    z-index: 2;
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 48px;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 80px;
    align-items: center;
    width: 100%;
  }
  
  .hero-content {
    .hero-badge {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 8px 16px;
      background: rgba($primary-color, 0.08);
      border-radius: 50px;
      font-size: 0.9rem;
      color: $primary-color;
      font-weight: 500;
      margin-bottom: 24px;
      
      .badge-dot {
        width: 8px;
        height: 8px;
        background: $primary-color;
        border-radius: 50%;
        animation: pulse 2s infinite;
      }
    }
  }
  
  .hero-title {
    font-family: $font-display;
    font-size: 3.2rem;
    font-weight: 700;
    line-height: 1.2;
    margin-bottom: 24px;
    
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
    font-size: 1.15rem;
    color: $text-secondary;
    line-height: 1.8;
    margin-bottom: 36px;
    max-width: 520px;
  }
  
  .hero-actions {
    display: flex;
    gap: 16px;
    margin-bottom: 48px;
  }
  
  .btn-primary {
    display: inline-flex;
    align-items: center;
    gap: 10px;
    padding: 16px 32px;
    background: linear-gradient(135deg, $primary-color, $primary-light);
    color: white;
    border-radius: 16px;
    font-weight: 600;
    font-size: 1.05rem;
    transition: all 0.3s ease;
    box-shadow: 0 8px 24px rgba($primary-color, 0.3);
    
    &:hover {
      transform: translateY(-3px);
      box-shadow: 0 12px 32px rgba($primary-color, 0.4);
    }
  }
  
  .btn-secondary {
    display: inline-flex;
    align-items: center;
    padding: 16px 32px;
    border: 2px solid rgba($text-secondary, 0.2);
    border-radius: 16px;
    font-weight: 600;
    color: $text-secondary;
    transition: all 0.3s ease;
    
    &:hover {
      border-color: $primary-color;
      color: $primary-color;
      background: rgba($primary-color, 0.04);
    }
  }
  
  .hero-stats {
    display: flex;
    align-items: center;
    gap: 32px;
    
    .stat {
      display: flex;
      flex-direction: column;
      
      .stat-number {
        font-family: $font-display;
        font-size: 1.8rem;
        font-weight: 700;
        color: $text-primary;
      }
      
      .stat-label {
        font-size: 0.9rem;
        color: $text-secondary;
      }
    }
    
    .stat-divider {
      width: 1px;
      height: 40px;
      background: rgba($text-secondary, 0.2);
    }
  }
  
  .hero-visual {
    position: relative;
    
    .hero-image-wrapper {
      position: relative;
      
      .main-image {
        position: relative;
        border-radius: 32px;
        overflow: hidden;
        box-shadow: 0 32px 64px rgba(0, 0, 0, 0.12);
        
        img {
          width: 100%;
          height: 480px;
          object-fit: cover;
          display: block;
        }
        
        &::after {
          content: '';
          position: absolute;
          inset: 0;
          background: linear-gradient(180deg, transparent 50%, rgba(0,0,0,0.1) 100%);
        }
      }
      
      .floating-card {
        position: absolute;
        background: white;
        padding: 16px 20px;
        border-radius: 16px;
        box-shadow: 0 12px 32px rgba(0, 0, 0, 0.12);
        display: flex;
        align-items: center;
        gap: 12px;
        animation: float 4s ease-in-out infinite;
        
        .card-icon {
          width: 40px;
          height: 40px;
          background: linear-gradient(135deg, $primary-color, $primary-light);
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          
          svg {
            width: 20px;
            height: 20px;
            color: white;
          }
          
          &.star {
            background: linear-gradient(135deg, #FFD700, #FFA500);
          }
        }
        
        .card-text {
          display: flex;
          flex-direction: column;
          
          .card-title {
            font-weight: 700;
            font-size: 0.95rem;
            color: $text-primary;
          }
          
          .card-subtitle {
            font-size: 0.8rem;
            color: $text-secondary;
          }
        }
        
        &.card-1 {
          top: 10%;
          left: -60px;
          animation-delay: 0s;
        }
        
        &.card-2 {
          top: 40%;
          right: -40px;
          animation-delay: 1.5s;
        }
        
        &.card-3 {
          bottom: 15%;
          left: -30px;
          animation-delay: 3s;
        }
      }
    }
  }
}

@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-12px);
  }
}

@keyframes pulse {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}

.services-section {
  padding: 100px 48px;
  background: white;
  
  .section-container {
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .section-header {
    text-align: center;
    margin-bottom: 60px;
    
    .section-label {
      display: inline-block;
      padding: 6px 16px;
      background: rgba($primary-color, 0.08);
      color: $primary-color;
      font-size: 0.85rem;
      font-weight: 600;
      border-radius: 50px;
      margin-bottom: 16px;
    }
    
    .section-title {
      font-family: $font-display;
      font-size: 2.4rem;
      font-weight: 700;
      color: $text-primary;
      margin-bottom: 12px;
    }
    
    .section-desc {
      font-size: 1.05rem;
      color: $text-secondary;
    }
    
    &.light {
      .section-label {
        background: rgba(white, 0.15);
        color: white;
      }
      
      .section-title {
        color: white;
      }
      
      .section-desc {
        color: rgba(white, 0.8);
      }
    }
  }
  
  .services-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 24px;
    
    @media (max-width: 1200px) {
      grid-template-columns: repeat(3, 1fr);
    }
    
    @media (max-width: 900px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .service-card {
    background: white;
    border-radius: 24px;
    overflow: hidden;
    cursor: pointer;
    transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    border: 1px solid rgba(0, 0, 0, 0.06);
    animation: fadeInUp 0.6s ease backwards;
    
    &:hover {
      transform: translateY(-12px);
      box-shadow: 0 24px 48px rgba($primary-color, 0.15);
      border-color: rgba($primary-color, 0.2);
      
      .card-image img {
        transform: scale(1.1);
      }
      
      .service-btn {
        background: linear-gradient(135deg, $primary-color, $primary-light);
        color: white;
      }
    }
    
    .card-image {
      position: relative;
      height: 180px;
      overflow: hidden;
      
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.5s ease;
      }
      
      .card-overlay {
        position: absolute;
        inset: 0;
        background: linear-gradient(180deg, transparent 0%, rgba(0,0,0,0.4) 100%);
      }
      
      .card-tag {
        position: absolute;
        top: 12px;
        left: 12px;
        padding: 4px 12px;
        background: linear-gradient(135deg, $primary-color, $primary-light);
        color: white;
        font-size: 0.75rem;
        font-weight: 600;
        border-radius: 50px;
      }
    }
    
    .card-body {
      padding: 20px;
      
      .card-icon {
        width: 48px;
        height: 48px;
        background: rgba($primary-color, 0.1);
        border-radius: 14px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-bottom: 12px;
        
        .el-icon {
          font-size: 1.4rem;
          color: $primary-color;
        }
      }
      
      .service-name {
        font-size: 1.15rem;
        font-weight: 700;
        color: $text-primary;
        margin-bottom: 8px;
      }
      
      .service-desc {
        font-size: 0.85rem;
        color: $text-secondary;
        line-height: 1.6;
        margin-bottom: 16px;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        overflow: hidden;
      }
      
      .service-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-top: 16px;
        border-top: 1px solid rgba(0, 0, 0, 0.06);
        
        .service-price {
          .price-currency {
            font-size: 0.9rem;
            color: $primary-color;
            font-weight: 600;
          }
          
          .price-value {
            font-size: 1.4rem;
            font-weight: 800;
            color: $primary-color;
          }
          
          .price-unit {
            font-size: 0.8rem;
            color: $text-light;
          }
        }
        
        .service-btn {
          display: flex;
          align-items: center;
          gap: 4px;
          padding: 8px 16px;
          background: rgba($primary-color, 0.08);
          color: $primary-color;
          font-size: 0.85rem;
          font-weight: 600;
          border-radius: 50px;
          transition: all 0.3s ease;
        }
      }
    }
  }
  
  .section-action {
    text-align: center;
    margin-top: 48px;
    
    .view-all-btn {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 14px 32px;
      background: transparent;
      border: 2px solid $primary-color;
      color: $primary-color;
      font-weight: 600;
      border-radius: 50px;
      transition: all 0.3s ease;
      
      &:hover {
        background: linear-gradient(135deg, $primary-color, $primary-light);
        color: white;
        border-color: transparent;
        transform: translateY(-2px);
        box-shadow: 0 8px 24px rgba($primary-color, 0.3);
      }
    }
  }
}

.features-section {
  position: relative;
  padding: 100px 48px;
  background: linear-gradient(135deg, $secondary-color 0%, #1a252f 100%);
  overflow: hidden;
  
  .features-bg-pattern {
    position: absolute;
    inset: 0;
    background-image: 
      radial-gradient(circle at 20% 50%, rgba($primary-color, 0.1) 0%, transparent 50%),
      radial-gradient(circle at 80% 80%, rgba($primary-light, 0.08) 0%, transparent 40%);
  }
  
  .section-container {
    position: relative;
    z-index: 2;
    max-width: 1400px;
    margin: 0 auto;
  }
  
  .features-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 32px;
    
    @media (max-width: 1000px) {
      grid-template-columns: repeat(2, 1fr);
    }
  }
  
  .feature-item {
    position: relative;
    text-align: center;
    padding: 40px 24px;
    background: rgba(white, 0.03);
    border: 1px solid rgba(white, 0.08);
    border-radius: 24px;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(white, 0.06);
      transform: translateY(-8px);
      
      .feature-icon {
        transform: scale(1.1);
        box-shadow: 0 12px 32px rgba($primary-color, 0.4);
      }
      
      .feature-decoration {
        opacity: 1;
      }
    }
    
    .feature-icon {
      width: 72px;
      height: 72px;
      margin: 0 auto 20px;
      background: linear-gradient(135deg, $primary-color, $primary-light);
      border-radius: 20px;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: all 0.3s ease;
      box-shadow: 0 8px 24px rgba($primary-color, 0.3);
      
      svg {
        width: 32px;
        height: 32px;
        color: white;
      }
    }
    
    h3 {
      font-size: 1.2rem;
      font-weight: 700;
      color: white;
      margin-bottom: 12px;
    }
    
    p {
      font-size: 0.9rem;
      color: rgba(white, 0.7);
      line-height: 1.7;
    }
    
    .feature-decoration {
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translateX(-50%);
      width: 60px;
      height: 3px;
      background: linear-gradient(90deg, $primary-color, $primary-light);
      border-radius: 2px;
      opacity: 0;
      transition: opacity 0.3s ease;
    }
  }
}

.cta-section {
  padding: 80px 48px;
  background: linear-gradient(180deg, #FAFAFA 0%, #FFFFFF 100%);
  
  .cta-container {
    position: relative;
    max-width: 1000px;
    margin: 0 auto;
    background: linear-gradient(135deg, $primary-color, $primary-light);
    border-radius: 32px;
    padding: 64px;
    text-align: center;
    overflow: hidden;
    box-shadow: 0 24px 64px rgba($primary-color, 0.3);
  }
  
  .cta-content {
    position: relative;
    z-index: 2;
    
    h2 {
      font-family: $font-display;
      font-size: 2.2rem;
      font-weight: 700;
      color: white;
      margin-bottom: 12px;
    }
    
    p {
      font-size: 1.1rem;
      color: rgba(white, 0.9);
      margin-bottom: 32px;
    }
    
    .cta-buttons {
      display: flex;
      justify-content: center;
      gap: 16px;
      
      .cta-btn-primary {
        display: inline-flex;
        align-items: center;
        gap: 8px;
        padding: 16px 36px;
        background: white;
        color: $primary-color;
        font-weight: 700;
        font-size: 1.05rem;
        border-radius: 50px;
        transition: all 0.3s ease;
        
        &:hover {
          transform: translateY(-3px);
          box-shadow: 0 12px 32px rgba(0, 0, 0, 0.2);
        }
      }
      
      .cta-btn-secondary {
        display: inline-flex;
        align-items: center;
        padding: 16px 36px;
        background: transparent;
        border: 2px solid rgba(white, 0.5);
        color: white;
        font-weight: 600;
        font-size: 1.05rem;
        border-radius: 50px;
        transition: all 0.3s ease;
        
        &:hover {
          background: rgba(white, 0.15);
          border-color: white;
        }
      }
    }
  }
  
  .cta-decoration {
    position: absolute;
    inset: 0;
    pointer-events: none;
    
    .deco-circle {
      position: absolute;
      border-radius: 50%;
      background: rgba(white, 0.1);
      
      &:nth-child(1) {
        width: 300px;
        height: 300px;
        top: -150px;
        right: -100px;
      }
      
      &:nth-child(2) {
        width: 200px;
        height: 200px;
        bottom: -100px;
        left: -50px;
      }
      
      &:nth-child(3) {
        width: 150px;
        height: 150px;
        top: 50%;
        right: 10%;
        transform: translateY(-50%);
      }
    }
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
