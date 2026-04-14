import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/UserLayout.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/user/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'services',
        name: 'Services',
        component: () => import('@/views/user/ServiceList.vue'),
        meta: { title: '服务列表' }
      },
      {
        path: 'service/:id',
        name: 'ServiceDetail',
        component: () => import('@/views/user/ServiceDetail.vue'),
        meta: { title: '服务详情' }
      },
      {
        path: 'orders',
        name: 'Orders',
        component: () => import('@/views/user/OrderList.vue'),
        meta: { title: '我的订单', requiresAuth: true }
      },
      {
        path: 'order/:id',
        name: 'OrderDetail',
        component: () => import('@/views/user/OrderDetail.vue'),
        meta: { title: '订单详情', requiresAuth: true }
      },
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/user/Profile.vue'),
        meta: { title: '个人中心', requiresAuth: true }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/user/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/user/Register.vue'),
    meta: { title: '注册' }
  },
  {
    path: '/worker',
    component: () => import('@/layouts/WorkerLayout.vue'),
    meta: { requiresAuth: true, requiresWorker: true },
    children: [
      {
        path: '',
        name: 'WorkerDashboard',
        component: () => import('@/views/worker/Dashboard.vue'),
        meta: { title: '工作台' }
      },
      {
        path: 'orders',
        name: 'WorkerOrders',
        component: () => import('@/views/worker/Orders.vue'),
        meta: { title: '我的订单' }
      },
      {
        path: 'profile',
        name: 'WorkerProfile',
        component: () => import('@/views/worker/Profile.vue'),
        meta: { title: '个人信息' }
      }
    ]
  },
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      {
        path: '',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/Dashboard.vue'),
        meta: { title: '数据概览' }
      },
      {
        path: 'users',
        name: 'AdminUsers',
        component: () => import('@/views/admin/UserManage.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'workers',
        name: 'AdminWorkers',
        component: () => import('@/views/admin/WorkerManage.vue'),
        meta: { title: '服务人员管理' }
      },
      {
        path: 'services',
        name: 'AdminServices',
        component: () => import('@/views/admin/ServiceManage.vue'),
        meta: { title: '服务类目管理' }
      },
      {
        path: 'orders',
        name: 'AdminOrders',
        component: () => import('@/views/admin/OrderManage.vue'),
        meta: { title: '订单管理' }
      },
      {
        path: 'reviews',
        name: 'AdminReviews',
        component: () => import('@/views/admin/ReviewManage.vue'),
        meta: { title: '评价管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 家政服务系统` : '家政服务系统'
  
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({ name: 'Login', query: { redirect: to.fullPath } })
    return
  }
  
  if (to.meta.requiresAdmin && userStore.user?.role !== 2) {
    next({ name: 'Home' })
    return
  }
  
  if (to.meta.requiresWorker && userStore.user?.role !== 1) {
    next({ name: 'Home' })
    return
  }
  
  next()
})

export default router
