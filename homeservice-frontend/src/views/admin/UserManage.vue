<template>
  <div class="user-manage-page">
    <div class="page-header">
      <h2>用户管理</h2>
      <el-input
        v-model="keyword"
        placeholder="搜索用户"
        style="width: 250px"
        clearable
        @clear="loadUsers"
        @keyup.enter="loadUsers"
      >
        <template #append>
          <el-button :icon="Search" @click="loadUsers" />
        </template>
      </el-input>
    </div>
    
    <el-table :data="users" stripe v-loading="loading">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="nickname" label="昵称" width="150" />
      <el-table-column prop="phone" label="手机号" width="150" />
      <el-table-column prop="role" label="角色" width="120">
        <template #default="{ row }">
          <el-tag :type="getRoleType(row.role)">{{ getRoleName(row.role) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '正常' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="注册时间" width="180" />
      <el-table-column label="操作" fixed="right" width="120">
        <template #default="{ row }">
          <el-button
            v-if="row.role !== 2"
            :type="row.status === 1 ? 'danger' : 'success'"
            size="small"
            link
            @click="handleStatusChange(row)"
          >
            {{ row.status === 1 ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="pagination">
      <el-pagination
        v-model:current-page="current"
        :page-size="10"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadUsers"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { getUserList, updateUserStatus } from '@/api/user'

const users = ref([])
const total = ref(0)
const current = ref(1)
const keyword = ref('')
const loading = ref(false)

onMounted(() => {
  loadUsers()
})

const loadUsers = async () => {
  loading.value = true
  try {
    const res = await getUserList({
      current: current.value,
      size: 10,
      keyword: keyword.value
    })
    users.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('获取用户列表失败', error)
  } finally {
    loading.value = false
  }
}

const getRoleType = (role) => {
  const types = {
    0: 'primary',
    1: 'warning',
    2: 'danger'
  }
  return types[role] || 'info'
}

const getRoleName = (role) => {
  const names = {
    0: '普通用户',
    1: '服务人员',
    2: '管理员'
  }
  return names[role] || '未知'
}

const handleStatusChange = async (user) => {
  const newStatus = user.status === 1 ? 0 : 1
  const action = newStatus === 0 ? '禁用' : '启用'
  
  try {
    await ElMessageBox.confirm(`确定要${action}该用户吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await updateUserStatus(user.id, newStatus)
    ElMessage.success(`${action}成功`)
    loadUsers()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('操作失败', error)
    }
  }
}
</script>

<style lang="scss" scoped>
.user-manage-page {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  
  .page-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: $spacing-lg;
    
    h2 {
      font-size: 1.2rem;
      font-weight: 600;
      color: $text-primary;
    }
  }
  
  .pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: $spacing-lg;
  }
}
</style>
