<?php

namespace App\Enums;

enum ShippingStatus: string
{
    case PENDING    = 'pending';     // Vừa tạo
    case CONFIRMED  = 'confirmed';   // Đã xác nhận
    case DELIVERING = 'delivering';  // Đang giao hàng
    case DELIVERED  = 'delivered';   // Đã giao
    case CANCELLED  = 'cancelled';   // Đã hủy
}