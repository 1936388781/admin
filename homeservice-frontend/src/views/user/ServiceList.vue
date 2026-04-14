<template>
  <div class="service-list-page">
    <div class="page-header">
      <div class="header-bg">
        <div class="bg-shape shape-1"></div>
        <div class="bg-shape shape-2"></div>
      </div>
      <div class="header-content">
        <div class="header-badge">
          <span class="badge-dot"></span>
          专业家政服务
        </div>
        <h1>找到最适合您的家政服务</h1>
        <p>覆盖日常保洁、深度清洁、母婴护理等全方位家政需求</p>
      </div>
      <div class="header-decoration"></div>
    </div>

    <div class="search-bar">
      <div class="search-wrapper">
        <el-input
          v-model="searchKeyword"
          placeholder="搜索您需要的服务..."
          class="search-input"
          :prefix-icon="Search"
        />
      </div>
    </div>

    <div class="category-tabs">
      <button
        v-for="cat in categories"
        :key="cat"
        :class="['category-tab', { active: selectedCategory === cat }]"
        @click="selectedCategory = cat"
      >
        <span class="tab-icon" v-if="getCategoryIcon(cat)">
          <component :is="getCategoryIcon(cat)" />
        </span>
        {{ cat }}
      </button>
    </div>

    <div class="services-grid">
      <div
        v-for="(service, index) in filteredServices"
        :key="service.id"
        class="service-card"
        :style="{ animationDelay: `${index * 0.08}s` }"
        @click="goToDetail(service.id)"
      >
        <div class="card-image-wrapper">
          <img :src="service.image || defaultImage" :alt="service.name" class="card-image" />
          <div class="card-overlay"></div>
          <div class="card-badge">{{ service.tag }}</div>
          <div class="card-hover-effect"></div>
        </div>
        <div class="card-content">
          <div class="card-header">
            <h3>{{ service.name }}</h3>
            <div class="rating">
              <svg class="star-icon" viewBox="0 0 24 24" fill="currentColor">
                <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
              </svg>
              <span>4.9</span>
            </div>
          </div>
          <p class="card-description">{{ service.description }}</p>
          <div class="card-features">
            <span v-for="feat in (service.features || []).slice(0, 3)" :key="feat" class="feature-tag">
              {{ feat }}
            </span>
          </div>
          <div class="card-footer">
            <div class="price-info">
              <span class="price">¥{{ service.price }}</span>
              <span class="unit">/次</span>
            </div>
            <el-button type="primary" class="book-btn">
              立即预约
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="filteredServices.length === 0" class="empty-state">
      <div class="empty-icon">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
          <circle cx="11" cy="11" r="8"/>
          <path d="m21 21-4.35-4.35"/>
        </svg>
      </div>
      <p>暂无匹配的服务</p>
      <span>试试其他关键词吧</span>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCategories } from '@/api/service'
import { Search } from '@element-plus/icons-vue'

const router = useRouter()
const searchKeyword = ref('')
const selectedCategory = ref('全部')
const services = ref([])

const categories = ['全部', '保洁', '母婴', '清洗', '搬家', '维修']

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

const serviceFeatures = {
  '日常保洁': ['日常维护', '分区清洁', '定时服务'],
  '深度保洁': ['全屋深度', '除螨', '高温消毒'],
  '月嫂服务': ['专业资质', '全程陪护', '科学喂养'],
  '保姆服务': ['经验丰富', '家常做饭', '照顾老人'],
  '家电清洗': ['深度拆洗', '高温蒸汽', '杀菌消毒'],
  '搬家服务': ['全程搬运', '物品打包', '新家还原'],
  '管道疏通': ['快速上门', '工具专业', '售后保障'],
  '除甲醛': ['专业检测', '源头治理', '安全环保']
}

const categoryIcons = {
  '全部': 'Grid',
  '保洁': 'Brush',
  '母婴': 'User',
  '清洗': 'Clean',
  '搬家': 'Van',
  '维修': 'Tools'
}

const getCategoryIcon = (cat) => {
  return categoryIcons[cat] || null
}

onMounted(async () => {
  try {
    const res = await getCategories()
    services.value = res.data.map(item => ({
      ...item,
      image: serviceImages[item.name] || defaultImage,
      tag: serviceTags[item.name] || '服务',
      features: serviceFeatures[item.name] || ['专业服务', '品质保障']
    }))
  } catch (error) {
    console.error('获取服务列表失败', error)
  }
})

const filteredServices = computed(() => {
  let result = services.value
  if (selectedCategory.value !== '全部') {
    result = result.filter(s => s.name.includes(selectedCategory.value))
  }
  if (searchKeyword.value) {
    result = result.filter(s =>
      s.name.includes(searchKeyword.value) ||
      s.description.includes(searchKeyword.value)
    )
  }
  return result
})

const goToDetail = (id) => {
  router.push(`/service/${id}`)
}
</script>

<style lang="scss" scoped>
.service-list-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #F8FAFC 0%, #FFFFFF 100%);
  padding-bottom: 80px;
}

.page-header {
  position: relative;
  text-align: center;
  padding: 60px 24px 80px;
  background: linear-gradient(135deg, #E67E22 0%, #F39C12 50%, #E67E22 100%);
  overflow: hidden;

  .header-bg {
    position: absolute;
    inset: 0;
    
    .bg-shape {
      position: absolute;
      border-radius: 50%;
      opacity: 0.15;
    }
    
    .shape-1 {
      width: 400px;
      height: 400px;
      background: white;
      top: -200px;
      right: -100px;
    }
    
    .shape-2 {
      width: 300px;
      height: 300px;
      background: white;
      bottom: -150px;
      left: -50px;
    }
  }

  .header-content {
    position: relative;
    z-index: 2;
    
    .header-badge {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      padding: 8px 20px;
      background: rgba(255, 255, 255, 0.25);
      border-radius: 50px;
      color: white;
      font-size: 0.9rem;
      font-weight: 500;
      margin-bottom: 20px;
      backdrop-filter: blur(10px);
      
      .badge-dot {
        width: 8px;
        height: 8px;
        background: white;
        border-radius: 50%;
        animation: pulse 2s infinite;
      }
    }
  }

  h1 {
    font-family: $font-display;
    font-size: 2.8rem;
    font-weight: 700;
    color: white;
    margin: 0 0 12px;
    text-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  }

  p {
    font-size: 1.1rem;
    color: rgba(255, 255, 255, 0.9);
    margin: 0;
  }

  .header-decoration {
    position: absolute;
    bottom: -2px;
    left: 0;
    right: 0;
    height: 60px;
    background: #FFFFFF;
    clip-path: ellipse(55% 100% at 50% 100%);
  }
}

.search-bar {
  max-width: 600px;
  margin: -40px auto 32px;
  padding: 0 24px;
  position: relative;
  z-index: 10;

  .search-wrapper {
    background: white;
    border-radius: 20px;
    padding: 8px;
    box-shadow: 0 8px 32px rgba(230, 126, 34, 0.15);
  }

  .search-input {
    width: 100%;

    :deep(.el-input__wrapper) {
      padding: 4px 16px;
      border-radius: 16px;
      box-shadow: none;
      background: transparent;

      &:hover, &.is-focus {
        box-shadow: none;
      }
      
      .el-input__inner {
        font-size: 1rem;
      }
    }
  }
}

.category-tabs {
  display: flex;
  justify-content: center;
  gap: 12px;
  padding: 0 24px;
  margin-bottom: 48px;
  flex-wrap: wrap;

  .category-tab {
    display: flex;
    align-items: center;
    gap: 6px;
    padding: 10px 24px;
    border: none;
    background: white;
    border-radius: 50px;
    font-size: 0.95rem;
    color: $text-secondary;
    cursor: pointer;
    transition: all 0.3s ease;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.04);

    .tab-icon {
      font-size: 1.1rem;
    }

    &:hover {
      color: $primary-color;
      transform: translateY(-2px);
      box-shadow: 0 4px 16px rgba(230, 126, 34, 0.12);
    }

    &.active {
      background: linear-gradient(135deg, $primary-color, $primary-light);
      color: white;
      box-shadow: 0 6px 20px rgba(230, 126, 34, 0.35);
      
      .tab-icon {
        color: white;
      }
    }
  }
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 24px;

  @media (max-width: 1200px) {
    grid-template-columns: repeat(3, 1fr);
  }

  @media (max-width: 900px) {
    grid-template-columns: repeat(2, 1fr);
  }

  @media (max-width: 600px) {
    grid-template-columns: 1fr;
  }
}

.service-card {
  background: white;
  border-radius: 24px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  animation: fadeInUp 0.6s ease backwards;
  border: 1px solid rgba(0, 0, 0, 0.04);

  &:hover {
    transform: translateY(-10px);
    box-shadow: 0 24px 48px rgba(230, 126, 34, 0.18);
    border-color: rgba($primary-color, 0.2);

    .card-image {
      transform: scale(1.1);
    }

    .card-overlay {
      opacity: 0.4;
    }
    
    .card-hover-effect {
      opacity: 1;
    }
    
    .book-btn {
      background: linear-gradient(135deg, $primary-color, $primary-light);
      color: white;
      transform: scale(1.05);
    }
  }

  .card-image-wrapper {
    position: relative;
    height: 180px;
    overflow: hidden;

    .card-image {
      width: 100%;
      height: 100%;
      object-fit: cover;
      transition: transform 0.5s ease;
    }

    .card-overlay {
      position: absolute;
      inset: 0;
      background: linear-gradient(180deg, transparent 0%, rgba(44, 62, 80, 0.6) 100%);
      opacity: 0.5;
      transition: opacity 0.3s ease;
    }
    
    .card-hover-effect {
      position: absolute;
      inset: 0;
      background: linear-gradient(135deg, rgba($primary-color, 0.2) 0%, transparent 100%);
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    .card-badge {
      position: absolute;
      top: 12px;
      right: 12px;
      padding: 4px 14px;
      background: linear-gradient(135deg, $primary-color, $primary-light);
      color: white;
      font-size: 0.75rem;
      font-weight: 600;
      border-radius: 50px;
      box-shadow: 0 2px 8px rgba(230, 126, 34, 0.4);
    }
  }

  .card-content {
    padding: 20px;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 10px;

    h3 {
      font-size: 1.15rem;
      font-weight: 700;
      color: $text-primary;
      margin: 0;
    }

    .rating {
      display: flex;
      align-items: center;
      gap: 4px;
      color: #FFD700;
      font-size: 0.9rem;
      font-weight: 600;
      
      .star-icon {
        width: 14px;
        height: 14px;
      }
    }
  }

  .card-description {
    font-size: 0.85rem;
    color: $text-secondary;
    line-height: 1.6;
    margin: 0 0 14px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
  }

  .card-features {
    display: flex;
    flex-wrap: wrap;
    gap: 6px;
    margin-bottom: 16px;

    .feature-tag {
      padding: 4px 10px;
      background: rgba($primary-color, 0.08);
      color: $primary-color;
      font-size: 0.75rem;
      border-radius: 12px;
    }
  }

  .card-footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 16px;
    border-top: 1px solid rgba(0, 0, 0, 0.06);

    .price-info {
      display: flex;
      align-items: baseline;
      gap: 2px;

      .price {
        font-size: 1.5rem;
        font-weight: 800;
        color: $primary-color;
      }

      .unit {
        font-size: 0.8rem;
        color: $text-light;
      }
    }

    .book-btn {
      padding: 10px 20px;
      border-radius: 50px;
      background: rgba($primary-color, 0.08);
      color: $primary-color;
      border: none;
      font-weight: 600;
      font-size: 0.9rem;
      transition: all 0.3s ease;
    }
  }
}

.empty-state {
  text-align: center;
  padding: 80px 24px;
  
  .empty-icon {
    width: 80px;
    height: 80px;
    margin: 0 auto 20px;
    background: rgba($primary-color, 0.08);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    
    svg {
      width: 40px;
      height: 40px;
      color: $text-light;
    }
  }

  p {
    font-size: 1.2rem;
    font-weight: 600;
    color: $text-primary;
    margin-bottom: 8px;
  }
  
  span {
    font-size: 0.9rem;
    color: $text-light;
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

@keyframes pulse {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
}
</style>
