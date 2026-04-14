<template>
  <div class="profile-page">
    <div class="page-container">
      <div class="profile-card">
        <div class="profile-header">
          <el-avatar :size="100" :src="userInfo.avatar">
            {{ userInfo.nickname?.charAt(0) }}
          </el-avatar>
          <div class="profile-info">
            <h2>{{ userInfo.nickname }}</h2>
            <p>{{ userInfo.phone }}</p>
          </div>
        </div>
        
        <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="100px"
          class="profile-form"
        >
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="form.nickname" placeholder="请输入昵称" />
          </el-form-item>
          
          <el-form-item label="头像">
            <el-input v-model="form.avatar" placeholder="请输入头像URL" />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" :loading="loading" @click="handleUpdate">
              保存修改
            </el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <div class="password-card">
        <h3>修改密码</h3>
        <el-form
          ref="passwordFormRef"
          :model="passwordForm"
          :rules="passwordRules"
          label-width="100px"
        >
          <el-form-item label="原密码" prop="oldPassword">
            <el-input v-model="passwordForm.oldPassword" type="password" show-password placeholder="请输入原密码" />
          </el-form-item>
          
          <el-form-item label="新密码" prop="newPassword">
            <el-input v-model="passwordForm.newPassword" type="password" show-password placeholder="请输入新密码" />
          </el-form-item>
          
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input v-model="passwordForm.confirmPassword" type="password" show-password placeholder="请确认新密码" />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" :loading="passwordLoading" @click="handlePasswordUpdate">
              修改密码
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { getUserInfo, updateUserInfo } from '@/api/user'
import { updatePassword } from '@/api/auth'

const userStore = useUserStore()

const userInfo = ref({})
const formRef = ref()
const passwordFormRef = ref()
const loading = ref(false)
const passwordLoading = ref(false)

const form = reactive({
  nickname: '',
  avatar: ''
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const rules = {
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ]
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

onMounted(async () => {
  try {
    const res = await getUserInfo()
    userInfo.value = res.data
    form.nickname = res.data.nickname
    form.avatar = res.data.avatar || ''
  } catch (error) {
    console.error('获取用户信息失败', error)
  }
})

const handleUpdate = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  loading.value = true
  try {
    const res = await updateUserInfo(form)
    userInfo.value = res.data
    userStore.user = res.data
    localStorage.setItem('user', JSON.stringify(res.data))
    ElMessage.success('修改成功')
  } catch (error) {
    console.error('修改失败', error)
  } finally {
    loading.value = false
  }
}

const handlePasswordUpdate = async () => {
  const valid = await passwordFormRef.value.validate().catch(() => false)
  if (!valid) return
  
  passwordLoading.value = true
  try {
    await updatePassword(passwordForm.oldPassword, passwordForm.newPassword)
    ElMessage.success('密码修改成功')
    passwordFormRef.value.resetFields()
  } catch (error) {
    console.error('修改密码失败', error)
  } finally {
    passwordLoading.value = false
  }
}
</script>

<style lang="scss" scoped>
.profile-page {
  padding: $spacing-xxl $spacing-lg;
  max-width: 800px;
  margin: 0 auto;
}

.profile-card {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-xl;
  box-shadow: $shadow-sm;
  margin-bottom: $spacing-xl;
  
  .profile-header {
    display: flex;
    align-items: center;
    gap: $spacing-lg;
    margin-bottom: $spacing-xl;
    padding-bottom: $spacing-xl;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);
    
    .profile-info {
      h2 {
        font-family: $font-display;
        font-size: 1.5rem;
        font-weight: 600;
        margin-bottom: $spacing-xs;
        color: $text-primary;
      }
      
      p {
        color: $text-secondary;
      }
    }
  }
  
  .profile-form {
    max-width: 500px;
  }
}

.password-card {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-xl;
  box-shadow: $shadow-sm;
  
  h3 {
    font-size: 1.2rem;
    font-weight: 600;
    margin-bottom: $spacing-lg;
    color: $text-primary;
  }
  
  .el-form {
    max-width: 500px;
  }
}
</style>
