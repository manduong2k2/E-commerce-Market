<?php

namespace App\Constant;

final class ResponseMessage
{
    public const UNAUTHORIZED = 'UNAUTHORIZED';
    public const INVALID_DATA = 'INVALID_DATA';
    public const BUDGET_IN_USE = 'BUDGET_IN_USE';
    public const PERMISSION_DENIED = 'PERMISSION_DENIED';
    public const BANK_ACCOUNT_EXISTS = 'BANK_ACCOUNT_EXISTS';
    public const CURRENCY_IN_USE = 'CURRENCY_IN_USE';
    public const SUCCESS = 'SUCCESS';
    public const FAILED_TO_STORE_LABOR_EXPENSE = 'FAILED_TO_STORE_LABOR_EXPENSE';
    public const NO_EXPENSE_CREATED_FROM_SUBMITTING_FLOW = 'NO_EXPENSE_CREATED_FROM_SUBMITTING_FLOW';
    public const ERROR_INTERNAL_TRANSFER_FAILED_TO_CREATE = 'ERROR_INTERNAL_TRANSFER_FAILED_TO_CREATE';
    public const ERROR_INTERNAL_TRANSFER_FAILED_TO_UPDATE = 'ERROR_INTERNAL_TRANSFER_FAILED_TO_UPDATE';
    public const ERROR_INTERNAL_TRANSFER_FAILED_TO_EXPORT = 'ERROR_INTERNAL_TRANSFER_FAILED_TO_EXPORT';
    public const INTERNAL_TRANSFER_IN_CLOSED_PERIOD = [
        'vi' => 'Không thể tạo giao dịch chuyển nội bộ trong kỳ kế toán đã khóa',
        'en' => 'Cannot create internal transfer within closed accounting period',
        'ja' => '締め処理済みの会計期間では内部振替を作成できません',
    ];
}
