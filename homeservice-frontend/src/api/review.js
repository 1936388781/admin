import request from '@/utils/request'

export function submitReview(data) {
  return request.post('/reviews', data)
}

export function getOrderReview(orderId) {
  return request.get(`/reviews/order/${orderId}`)
}

export function getWorkerReviews(workerId, params) {
  return request.get(`/reviews/worker/${workerId}`, { params })
}

export function getAdminReviews(params) {
  return request.get('/admin/reviews', { params })
}

export function deleteReview(id) {
  return request.delete(`/admin/reviews/${id}`)
}
