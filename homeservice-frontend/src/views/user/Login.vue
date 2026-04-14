<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-left">
        <div class="brand">
          <div class="logo">
            <svg viewBox="0 0 40 40" fill="none">
              <circle cx="20" cy="20" r="18" fill="url(#loginGradient)" />
              <path d="M12 22c0-4.4 3.6-8 8-8s8 3.6 8 8" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
              <circle cx="20" cy="24" r="3" fill="white"/>
              <defs>
                <linearGradient id="loginGradient" x1="0" y1="0" x2="40" y2="40">
                  <stop offset="0%" stop-color="#E67E22"/>
                  <stop offset="100%" stop-color="#F39C12"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <h1>家政服务系统</h1>
        </div>
        <p class="welcome-text">欢迎回来，请登录您的账户</p>
        <div class="features">
          <div class="feature-item">
            <el-icon><Check /></el-icon>
            <span>专业服务团队</span>
          </div>
          <div class="feature-item">
            <el-icon><Check /></el-icon>
            <span>安全可靠保障</span>
          </div>
          <div class="feature-item">
            <el-icon><Check /></el-icon>
            <span>贴心售后服务</span>
          </div>
        </div>
      </div>
      
      <div class="login-right">
        <div class="login-form-container">
          <h2>登录账户</h2>
          
          <el-form
            ref="formRef"
            :model="form"
            :rules="rules"
            class="login-form"
            @submit.prevent="handleLogin"
          >
            <el-form-item prop="phone">
              <el-input
                v-model="form.phone"
                placeholder="请输入手机号"
                size="large"
                :prefix-icon="Phone"
              />
            </el-form-item>
            
            <el-form-item prop="password">
              <el-input
                v-model="form.password"
                type="password"
                placeholder="请输入密码"
                size="large"
                :prefix-icon="Lock"
                show-password
              />
            </el-form-item>
            
            <el-form-item>
              <el-button
                type="primary"
                size="large"
                class="login-btn"
                :loading="loading"
                @click="handleLogin"
              >
                登录
              </el-button>
            </el-form-item>
          </el-form>
          
          <div class="login-footer">
            <span>还没有账户？</span>
            <router-link to="/register">立即注册</router-link>
          </div>
          
          <div class="demo-accounts">
            <p>演示账号：</p>
            <div class="accounts">
              <span @click="fillAdmin">管理员: 13800000000 / admin123</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Phone, Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const formRef = ref()
const loading = ref(false)

const form = reactive({
  phone: '',
  password: ''
})

const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  loading.value = true
  try {
    await userStore.login(form.phone, form.password)
    ElMessage.success('登录成功')
    
    const redirect = route.query.redirect || '/'
    router.push(redirect)
  } catch (error) {
    console.error('登录失败', error)
  } finally {
    loading.value = false
  }
}

const fillAdmin = () => {
  form.phone = '13800000000'
  form.password = 'admin123'
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #FFF9F5 0%, #FFF 50%, #F5F9FF 100%);
  padding: $spacing-lg;
}

.login-container {
  display: flex;
  width: 100%;
  max-width: 1000px;
  background: white;
  border-radius: $radius-xl;
  overflow: hidden;
  box-shadow: $shadow-lg;
}

.login-left {
  flex: 1;
  background: linear-gradient(135deg, $primary-color, $primary-light);
  padding: $spacing-xxl;
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: white;
  
  .brand {
    display: flex;
    align-items: center;
    gap: $spacing-md;
    margin-bottom: $spacing-lg;
    
    .logo {
      width: 50px;
      height: 50px;
      
      svg {
        width: 100%;
        height: 100%;
      }
    }
    
    h1 {
      font-family: $font-display;
      font-size: 1.8rem;
      font-weight: 600;
    }
  }
  
  .welcome-text {
    font-size: 1.2rem;
    opacity: 0.9;
    margin-bottom: $spacing-xl;
  }
  
  .features {
    .feature-item {
      display: flex;
      align-items: center;
      gap: $spacing-sm;
      margin-bottom: $spacing-md;
      font-size: 1rem;
      
      .el-icon {
        font-size: 1.2rem;
      }
    }
  }
}

.login-right {
  flex: 1;
  padding: $spacing-xxl;
  display: flex;
  align-items: center;
  justify-content: center;
  
  .login-form-container {
    width: 100%;
    max-width: 350px;
    
    h2 {
      font-family: $font-display;
      font-size: 1.8rem;
      font-weight: 600;
      margin-bottom: $spacing-xl;
      color: $text-primary;
    }
  }
  
  .login-form {
    .el-form-item {
      margin-bottom: $spacing-lg;
    }
    
    :deep(.el-input__wrapper) {
      border-radius: $radius-md;
      padding: 0 $spacing-md;
    }
  }
  
  .login-btn {
    width: 100%;
    height: 48px;
    font-size: 1rem;
    font-weight: 600;
    border-radius: $radius-md;
    background: linear-gradient(135deg, $primary-color, $primary-light);
    border: none;
    
    &:hover {
      opacity: 0.9;
    }
  }
  
  .login-footer {
    text-align: center;
    margin-top: $spacing-lg;
    color: $text-secondary;
    
    a {
      color: $primary-color;
      font-weight: 500;
      margin-left: $spacing-xs;
      
      &:hover {
        text-decoration: underline;
      }
    }
  }
  
  .demo-accounts {
    margin-top: $spacing-xl;
    padding-top: $spacing-lg;
    border-top: 1px solid rgba(0, 0, 0, 0.05);
    
    p {
      font-size: 0.85rem;
      color: $text-light;
      margin-bottom: $spacing-sm;
    }
    
    .accounts {
      span {
        display: block;
        font-size: 0.85rem;
        color: $text-secondary;
        cursor: pointer;
        padding: $spacing-xs 0;
        
        &:hover {
          color: $primary-color;
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .login-container {
    flex-direction: column;
  }
  
  .login-left {
    padding: $spacing-xl;
  }
  
  .login-right {
    padding: $spacing-xl;
  }
}
</style>
