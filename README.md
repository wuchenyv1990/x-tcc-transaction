# x-tcc-tx

一个原始的、侵入性的tcc代码示例，用以演示tcc基本思想</br>
一个简单的例子：order记录应付金额和已付金额，account记录账号余额，sponsor产生给账单付款操作，order已付金额增加&account可用余额扣除
<li>预留资源：try阶段直接扣款，`X_TCC_TRANSACTION`表记录tcc事务状态</li>
<li>定时任务：对try和cancel状态的`X_TCC_TRANSACTION`进行cancel操作</li>
<li>定时任务：对cofirm状态的`X_TCC_TRANSACTION`进行confirm操作</li>
<li>confirm：业务表不记录状态，因此直接删除当前`X_TCC_TRANSACTION`</li>