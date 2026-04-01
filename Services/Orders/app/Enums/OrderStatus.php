<?php

namespace App\Enums;

enum OrderStatus: string
{
    case PENDING        = 'pending';       // Vừa tạo
    case CONFIRMED      = 'confirmed';     // Đã xác nhận
    case PAID           = 'paid';          // Thanh toán thành công
    case PROCESSING     = 'processing';    // Đang xử lý
    case SHIPPED        = 'shipped';       // Đang giao hàng
    case DELIVERED      = 'delivered';     // Đã giao
    case CANCELLED      = 'cancelled';     // Đã hủy
    case FAILED         = 'failed';        // Lỗi
    case RETURNED       = 'returned';      // Trả hàng
    case REFUNDED       = 'refunded';      // Hoàn tiền
}
