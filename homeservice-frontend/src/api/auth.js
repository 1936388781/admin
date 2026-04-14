import request from '@/utils/request'

export function login(data) {
  return request.post('/auth/login', data)
}

export function register(data) {
  return request.post('/auth/register', data)
}

export function getCurrentUser() {
  return request.get('/auth/info')
}

export function updatePassword(oldPassword, newPassword) {
  return request.put('/auth/password', null, {
    params: { oldPassword, newPassword }
  })
}
