<template>
  <div class="register-page">
    <div class="register-container">
      <div class="register-left">
        <div class="brand">
          <div class="logo">
            <svg viewBox="0 0 40 40" fill="none">
              <circle cx="20" cy="20" r="18" fill="url(#registerGradient)" />
              <path d="M12 22c0-4.4 3.6-8 8-8s8 3.6 8 8" stroke="white" stroke-width="2.5" stroke-linecap="round"/>
              <circle cx="20" cy="24" r="3" fill="white"/>
              <defs>
                <linearGradient id="registerGradient" x1="0" y1="0" x2="40" y2="40">
                  <stop offset="0%" stop-color="#E67E22"/>
                  <stop offset="100%" stop-color="#F39C12"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <h1>家政服务系统</h1>
        </div>
        <p class="welcome-text">创建您的账户，开始体验专业家政服务</p>
      </div>
      
      <div class="register-right">
        <div class="register-form-container">
          <h2>注册账户</h2>
          
          <el-form
            ref="formRef"
            :model="form"
            :rules="rules"
            class="register-form"
            @submit.prevent="handleRegister"
          >
            <el-form-item prop="phone">
              <el-input
                v-model="form.phone"
                placeholder="请输入手机号"
                size="large"
                :prefix-icon="Phone"
              />
            </el-form-item>
            
            <el-form-item prop="nickname">
              <el-input
                v-model="form.nickname"
                placeholder="请输入昵称（选填）"
                size="large"
                :prefix-icon="User"
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
            
            <el-form-item prop="confirmPassword">
              <el-input
                v-model="form.confirmPassword"
                type="password"
                placeholder="请确认密码"
                size="large"
                :prefix-icon="Lock"
                show-password
              />
            </el-form-item>
            
            <el-form-item>
              <el-button
                type="primary"
                size="large"
                class="register-btn"
                :loading="loading"
                @click="handleRegister"
              >
                注册
              </el-button>
            </el-form-item>
          </el-form>
          
          <div class="register-footer">
            <span>已有账户？</span>
            <router-link to="/login">立即登录</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Phone, Lock, User } from '@element-plus/icons-vue'
import { register } from '@/api/auth'

const router = useRouter()

const formRef = ref()
const loading = ref(false)

const form = reactive({
  phone: '',
  nickname: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== form.password) {
    callback(new Error('两次密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  loading.value = true
  try {
    await register(form)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (error) {
    console.error('注册失败', error)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #FFF9F5 0%, #FFF 50%, #F5F9FF 100%);
  padding: $spacing-lg;
}

.register-container {
  display: flex;
  width: 100%;
  max-width: 1000px;
  background: white;
  border-radius: $radius-xl;
  overflow: hidden;
  box-shadow: $shadow-lg;
}

.register-left {
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
  }
}

.register-right {
  flex: 1;
  padding: $spacing-xxl;
  display: flex;
  align-items: center;
  justify-content: center;
  
  .register-form-container {
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
  
  .register-form {
    .el-form-item {
      margin-bottom: $spacing-md;
    }
    
    :deep(.el-input__wrapper) {
      border-radius: $radius-md;
      padding: 0 $spacing-md;
    }
  }
  
  .register-btn {
    width: 100%;
    height: 48px;
    font-size: 1rem;
    font-weight: 600;
    border-radius: $radius-md;
    background: linear-gradient(135deg, $primary-color, $primary-light);
    border: none;
    margin-top: $spacing-sm;
    
    &:hover {
      opacity: 0.9;
    }
  }
  
  .register-footer {
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
}

@media (max-width: 768px) {
  .register-container {
    flex-direction: column;
  }
  
  .register-left {
    padding: $spacing-xl;
  }
  
  .register-right {
    padding: $spacing-xl;
  }
}
</style>
