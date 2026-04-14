import request from '@/utils/request'

export function getStatistics() {
  return request.get('/admin/statistics')
}

export function getAdminUsers(params) {
  return request.get('/admin/users', { params })
}

export function adminUpdateUserStatus(id, status) {
  return request.put(`/admin/users/${id}/status`, null, { params: { status } })
}
