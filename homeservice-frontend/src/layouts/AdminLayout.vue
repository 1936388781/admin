<template>
  <div class="admin-layout">
    <aside class="sidebar" :class="{ collapsed: appStore.collapsed }">
      <div class="sidebar-header">
        <div class="logo">
          <svg viewBox="0 0 40 40" fill="none">
            <circle cx="20" cy="20" r="18" fill="url(#adminGradient)" />
            <path d="M12 22c0-4.4 3.6-8 8-8s8 3.6 8 8" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
            <circle cx="20" cy="24" r="3" fill="white"/>
            <defs>
              <linearGradient id="adminGradient" x1="0" y1="0" x2="40" y2="40">
                <stop offset="0%" stop-color="#2C3E50"/>
                <stop offset="100%" stop-color="#34495E"/>
              </linearGradient>
            </defs>
          </svg>
        </div>
        <span class="logo-text" v-show="!appStore.collapsed">管理后台</span>
      </div>
      
      <el-menu
        :default-active="$route.path"
        :collapse="appStore.collapsed"
        router
        class="sidebar-menu"
      >
        <el-menu-item index="/admin">
          <el-icon><DataAnalysis /></el-icon>
          <template #title>数据概览</template>
        </el-menu-item>
        <el-menu-item index="/admin/users">
          <el-icon><User /></el-icon>
          <template #title>用户管理</template>
        </el-menu-item>
        <el-menu-item index="/admin/workers">
          <el-icon><Avatar /></el-icon>
          <template #title>服务人员管理</template>
        </el-menu-item>
        <el-menu-item index="/admin/services">
          <el-icon><Grid /></el-icon>
          <template #title>服务类目管理</template>
        </el-menu-item>
        <el-menu-item index="/admin/orders">
          <el-icon><Document /></el-icon>
          <template #title>订单管理</template>
        </el-menu-item>
        <el-menu-item index="/admin/reviews">
          <el-icon><ChatDotSquare /></el-icon>
          <template #title>评价管理</template>
        </el-menu-item>
      </el-menu>
    </aside>
    
    <div class="main-container">
      <header class="header">
        <div class="header-left">
          <el-button 
            :icon="appStore.collapsed ? 'Expand' : 'Fold'" 
            @click="appStore.toggleCollapsed"
            text
          />
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
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
import { useAppStore } from '@/stores/app'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const appStore = useAppStore()
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
.admin-layout {
  display: flex;
  min-height: 100vh;
}

.sidebar {
  width: 240px;
  background: $secondary-color;
  transition: width $transition-normal;
  
  &.collapsed {
    width: 64px;
  }
  
  .sidebar-header {
    height: 64px;
    display: flex;
    align-items: center;
    padding: 0 $spacing-md;
    gap: $spacing-sm;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    
    .logo {
      width: 32px;
      height: 32px;
      flex-shrink: 0;
      
      svg {
        width: 100%;
        height: 100%;
      }
    }
    
    .logo-text {
      color: white;
      font-weight: 600;
      font-size: 1.1rem;
      white-space: nowrap;
    }
  }
  
  .sidebar-menu {
    border: none;
    background: transparent;
    
    :deep(.el-menu-item) {
      color: rgba(255, 255, 255, 0.7);
      
      &:hover,
      &.is-active {
        background: rgba(255, 255, 255, 0.1);
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
  
  .header-left {
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
    border-radius: $radius-md;
    transition: background $transition-fast;
    
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
