<template>
  <div class="worker-manage-page">
    <div class="page-header">
      <h2>服务人员管理</h2>
    </div>
    
    <el-empty description="功能开发中..." />
  </div>
</template>

<script setup>
</script>

<style lang="scss" scoped>
.worker-manage-page {
  background: white;
  border-radius: $radius-lg;
  padding: $spacing-lg;
  box-shadow: $shadow-sm;
  min-height: 400px;
  
  .page-header {
    margin-bottom: $spacing-lg;
    
    h2 {
      font-size: 1.2rem;
      font-weight: 600;
      color: $text-primary;
    }
  }
}
</style>
