<template>
  <div class="worker-layout">
    <aside class="sidebar">
      <div class="sidebar-header">
        <div class="logo">
          <svg viewBox="0 0 40 40" fill="none">
            <circle cx="20" cy="20" r="18" fill="url(#workerGradient)" />
            <path d="M12 22c0-4.4 3.6-8 8-8s8 3.6 8 8" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
            <circle cx="20" cy="24" r="3" fill="white"/>
            <defs>
              <linearGradient id="workerGradient" x1="0" y1="0" x2="40" y2="40">
                <stop offset="0%" stop-color="#27AE60"/>
                <stop offset="100%" stop-color="#2ECC71"/>
              </linearGradient>
            </defs>
          </svg>
        </div>
        <span class="logo-text">工作台</span>
      </div>
      
      <el-menu
        :default-active="$route.path"
        router
        class="sidebar-menu"
      >
        <el-menu-item index="/worker">
          <el-icon><HomeFilled /></el-icon>
          <template #title>工作台</template>
        </el-menu-item>
        <el-menu-item index="/worker/orders">
          <el-icon><Document /></el-icon>
          <template #title>我的订单</template>
        </el-menu-item>
        <el-menu-item index="/worker/profile">
          <el-icon><User /></el-icon>
          <template #title>个人信息</template>
        </el-menu-item>
      </el-menu>
    </aside>
    
    <div class="main-container">
      <header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/worker' }">工作台</el-breadcrumb-item>
            <el-breadcrumb-item>{{ $route.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <div class="header-right">
          <el-dropdown trigger="click" @command="handleCommand">
            <div class="user-info">
              <el-avatar :size="32" :src="userStore.user?.avatar">
                {{ userStore.user?.nickname?.charAt(0) }}
              </el-avatar>
              <span>{{ userStore.user?.nickname }}</span>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="home">返回前台</el-dropdown-item>
                <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>
      
      <main class="main">
        <router-view v-slot="{ Component }">
          <transition name="slide" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </main>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

const handleCommand = (command) => {
  switch (command) {
    case 'home':
      router.push('/')
      break
    case 'logout':
      userStore.logout()
      router.push('/')
      break
  }
}
</script>

<style lang="scss" scoped>
.worker-layout {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 220px;
  background: linear-gradient(180deg, #27AE60 0%, #2ECC71 100%);
  
  .sidebar-header {
    height: 64px;
    display: flex;
    align-items: center;
    padding: 0 $spacing-md;
    gap: $spacing-sm;
    
    .logo {
      width: 32px;
      height: 32px;
      
      svg {
        width: 100%;
        height: 100%;
      }
    }
    
    .logo-text {
      color: white;
      font-weight: 600;
      font-size: 1.1rem;
    }
  }
  
  .sidebar-menu {
    border: none;
    background: transparent;
    
    :deep(.el-menu-item) {
      color: rgba(255, 255, 255, 0.8);
      
      &:hover,
      &.is-active {
        background: rgba(255, 255, 255, 0.2);
        color: white;
      }
    }
  }
}

.main-container {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: $bg-primary;
}

.header {
  height: 64px;
  background: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 $spacing-lg;
  box-shadow: $shadow-sm;
  
  .user-info {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    cursor: pointer;
    padding: $spacing-xs $spacing-md;
    border-radius: $radius-md;
    
    &:hover {
      background: $bg-primary;
    }
  }
}

.main {
  flex: 1;
  padding: $spacing-lg;
  overflow-y: auto;
}
</style>
