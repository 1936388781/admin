<template>
  <div class="user-layout">
    <header class="header">
      <div class="header-content">
        <div class="logo" @click="$router.push('/')">
          <div class="logo-icon">
            <svg viewBox="0 0 40 40" fill="none">
              <circle cx="20" cy="20" r="18" fill="url(#gradient)" />
              <path d="M12 22c0-4.4 3.6-8 8-8s8 3.6 8 8" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
              <circle cx="20" cy="24" r="3" fill="white"/>
              <defs>
                <linearGradient id="gradient" x1="0" y1="0" x2="40" y2="40">
                  <stop offset="0%" stop-color="#E67E22"/>
                  <stop offset="100%" stop-color="#F39C12"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <span class="logo-text">家政服务</span>
        </div>
        
        <nav class="nav">
          <router-link to="/" class="nav-item" :class="{ active: $route.path === '/' }">首页</router-link>
          <router-link to="/services" class="nav-item" :class="{ active: $route.path === '/services' }">服务</router-link>
          <router-link to="/orders" class="nav-item" v-if="userStore.isLoggedIn">订单</router-link>
        </nav>
        
        <div class="header-right">
          <template v-if="userStore.isLoggedIn">
            <el-dropdown trigger="click" @command="handleCommand">
              <div class="user-info">
                <el-avatar :size="36" :src="userStore.user?.avatar">
                  {{ userStore.user?.nickname?.charAt(0) }}
                </el-avatar>
                <span class="username">{{ userStore.user?.nickname }}</span>
              </div>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                  <el-dropdown-item command="orders">我的订单</el-dropdown-item>
                  <el-dropdown-item command="worker" v-if="userStore.isWorker">工作台</el-dropdown-item>
                  <el-dropdown-item command="admin" v-if="userStore.isAdmin">管理后台</el-dropdown-item>
                  <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login" class="btn-login">登录</router-link>
            <router-link to="/register" class="btn-register">注册</router-link>
          </template>
        </div>
      </div>
    </header>
    
    <main class="main">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
    
    <footer class="footer">
      <div class="footer-content">
        <div class="footer-links">
          <a href="#">关于我们</a>
          <a href="#">服务条款</a>
          <a href="#">隐私政策</a>
          <a href="#">联系我们</a>
        </div>
        <p class="copyright">© 2024 家政服务系统 版权所有</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const handleCommand = (command) => {
  switch (command) {
    case 'profile':
      router.push('/profile')
      break
    case 'orders':
      router.push('/orders')
      break
    case 'worker':
      router.push('/worker')
      break
    case 'admin':
      router.push('/admin')
      break
    case 'logout':
      userStore.logout()
      router.push('/')
      break
  }
}
</script>

<style lang="scss" scoped>
.user-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  box-shadow: $shadow-sm;
  z-index: 100;
  
  .header-content {
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 $spacing-lg;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.logo {
  display: flex;
  align-items: center;
  gap: $spacing-sm;
  cursor: pointer;
  transition: transform $transition-fast;
  
  &:hover {
    transform: scale(1.02);
  }
  
  .logo-icon {
    width: 40px;
    height: 40px;
    
    svg {
      width: 100%;
      height: 100%;
    }
  }
  
  .logo-text {
    font-family: $font-display;
    font-size: 1.5rem;
    font-weight: 600;
    color: $text-primary;
  }
}

.nav {
  display: flex;
  gap: $spacing-xl;
  
  .nav-item {
    font-size: 1rem;
    font-weight: 500;
    color: $text-secondary;
    padding: $spacing-sm 0;
    position: relative;
    transition: color $transition-fast;
    
    &::after {
      content: '';
      position: absolute;
      bottom: 0;
      left: 0;
      width: 0;
      height: 2px;
      background: $primary-color;
      transition: width $transition-fast;
    }
    
    &:hover,
    &.active {
      color: $primary-color;
      
      &::after {
        width: 100%;
      }
    }
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: $spacing-md;
}

.user-info {
  display: flex;
  align-items: center;
  gap: $spacing-sm;
  cursor: pointer;
  padding: $spacing-xs $spacing-md;
  border-radius: $radius-lg;
  transition: background $transition-fast;
  
  &:hover {
    background: rgba($primary-color, 0.1);
  }
  
  .username {
    font-weight: 500;
    color: $text-primary;
  }
}

.btn-login,
.btn-register {
  padding: $spacing-sm $spacing-lg;
  border-radius: $radius-md;
  font-weight: 500;
  transition: all $transition-fast;
}

.btn-login {
  color: $primary-color;
  
  &:hover {
    background: rgba($primary-color, 0.1);
  }
}

.btn-register {
  background: linear-gradient(135deg, $primary-color, $primary-light);
  color: white;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba($primary-color, 0.3);
  }
}

.main {
  flex: 1;
  margin-top: 70px;
}

.footer {
  background: $secondary-color;
  padding: $spacing-xxl $spacing-lg;
  
  .footer-content {
    max-width: 1400px;
    margin: 0 auto;
    text-align: center;
  }
  
  .footer-links {
    display: flex;
    justify-content: center;
    gap: $spacing-xl;
    margin-bottom: $spacing-lg;
    
    a {
      color: rgba(255, 255, 255, 0.7);
      font-size: 0.9rem;
      
      &:hover {
        color: white;
      }
    }
  }
  
  .copyright {
    color: rgba(255, 255, 255, 0.5);
    font-size: 0.85rem;
  }
}
</style>
