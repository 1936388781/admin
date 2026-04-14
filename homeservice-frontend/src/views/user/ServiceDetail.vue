<template>
  <div class="service-detail-page">
    <div class="page-container" v-if="service">
      <div class="service-header">
        <div class="service-icon">
          <component :is="getServiceIcon(service.name)" />
        </div>
        <div class="service-info">
          <h1>{{ service.name }}</h1>
          <p>{{ service.description }}</p>
          <div class="price-info">
            <span class="label">服务价格</span>
            <span class="price">¥{{ service.price }}</span>
            <span class="unit">/次</span>
          </div>
        </div>
      </div>
      
      <div class="order-section">
        <h2>预约服务</h2>
        <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="100px"
          class="order-form"
        >
          <el-form-item label="预约时间" prop="appointmentTime">
            <el-date-picker
              v-model="form.appointmentTime"
              type="datetime"
              placeholder="选择预约时间"
              :disabled-date="disabledDate"
              style="width: 100%"
            />
          </el-form-item>
          
          <el-form-item label="联系人" prop="contactName">
            <el-input v-model="form.contactName" placeholder="请输入联系人姓名" />
          </el-form-item>
          
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
          </el-form-item>
          
          <el-form-item label="服务地址" prop="address">
            <el-input v-model="form.address" placeholder="请输入服务地址" />
          </el-form-item>
          
          <el-form-item label="备注">
            <el-input
              v-model="form.remark"
              type="textarea"
              :rows="3"
              placeholder="请输入备注信息（选填）"
            />
          </el-form-item>
          
          <el-form-item>
            <el-button type="primary" size="large" :loading="loading" @click="handleSubmit">
              提交订单
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getCategoryById } from '@/api/service'
import { createOrder } from '@/api/order'

const route = useRoute()
const router = useRouter()

const service = ref(null)
const formRef = ref()
const loading = ref(false)

const form = reactive({
  appointmentTime: '',
  contactName: '',
  contactPhone: '',
  address: '',
  remark: ''
})

const rules = {
  appointmentTime: [
    { required: true, message: '请选择预约时间', trigger: 'change' }
  ],
  contactName: [
    { required: true, message: '请输入联系人姓名', trigger: 'blur' }
  ],
  contactPhone: [
    { required: true, message: '请输入联系电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入服务地址', trigger: 'blur' }
  ]
}

onMounted(async () => {
  try {
    const res = await getCategoryById(route.params.id)
    service.value = res.data
  } catch (error) {
    console.error('获取服务详情失败', error)
  }
})

const disabledDate = (date) => {
  return date < new Date()
}

const handleSubmit = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  
  loading.value = true
  try {
    await createOrder({
      categoryId: service.value.id,
      ...form,
      totalPrice: service.value.price
    })
    ElMessage.success('订单提交成功')
    router.push('/orders')
  } catch (error) {
    console.error('提交订单失败', error)
  } finally {
    loading.value = false
  }
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
.service-detail-page {
  padding: $spacing-xxl $spacing-lg;
  max-width: 1000px;
  margin: 0 auto;
}

.service-header {
  display: flex;
  gap: $spacing-xl;
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-xl;
  margin-bottom: $spacing-xl;
  box-shadow: $shadow-sm;
  
  .service-icon {
    width: 120px;
    height: 120px;
    background: linear-gradient(135deg, rgba($primary-color, 0.1), rgba($primary-light, 0.1));
    border-radius: $radius-lg;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
    
    .el-icon {
      font-size: 3rem;
      color: $primary-color;
    }
  }
  
  .service-info {
    flex: 1;
    
    h1 {
      font-family: $font-display;
      font-size: 2rem;
      font-weight: 600;
      margin-bottom: $spacing-sm;
      color: $text-primary;
    }
    
    p {
      font-size: 1rem;
      color: $text-secondary;
      line-height: 1.6;
      margin-bottom: $spacing-lg;
    }
    
    .price-info {
      display: flex;
      align-items: baseline;
      gap: $spacing-sm;
      
      .label {
        font-size: 0.9rem;
        color: $text-secondary;
      }
      
      .price {
        font-size: 2rem;
        font-weight: 700;
        color: $primary-color;
      }
      
      .unit {
        font-size: 0.9rem;
        color: $text-light;
      }
    }
  }
}

.order-section {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-xl;
  box-shadow: $shadow-sm;
  
  h2 {
    font-family: $font-display;
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: $spacing-lg;
    color: $text-primary;
  }
  
  .order-form {
    max-width: 600px;
  }
}
</style>
