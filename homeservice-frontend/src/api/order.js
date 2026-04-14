import request from '@/utils/request'

export function createOrder(data) {
  return request.post('/orders', data)
}

export function getMyOrders(params) {
  return request.get('/orders', { params })
}

export function getOrderDetail(id) {
  return request.get(`/orders/${id}`)
}

export function cancelOrder(id, reason) {
  return request.put(`/orders/${id}/cancel`, null, { params: { reason } })
}

export function confirmOrder(id) {
  return request.put(`/orders/${id}/confirm`)
}

export function getAdminOrders(params) {
  return request.get('/admin/orders', { params })
}

export function assignOrder(id, workerId) {
  return request.put(`/admin/orders/${id}/assign`, null, { params: { workerId } })
}

export function handleOrderException(id, reason) {
  return request.put(`/admin/orders/${id}/exception`, null, { params: { reason } })
}

export function getWorkerOrders(params) {
  return request.get('/workers/orders', { params })
}

export function acceptOrder(id) {
  return request.put(`/workers/orders/${id}/accept`)
}

export function rejectOrder(id, reason) {
  return request.put(`/workers/orders/${id}/reject`, null, { params: { reason } })
}

export function startOrder(id) {
  return request.put(`/workers/orders/${id}/start`)
}
