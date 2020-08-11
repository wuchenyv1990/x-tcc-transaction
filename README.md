# x-tcc-transaction

一个原始的、侵入性的tcc代码示例，用以演示tcc基本思想</br>
一个简单的例子：order记录应付金额和已付金额，account记录账号余额，sponsor产生给账单付款操作，order已付金额增加&account可用余额扣除<br>
如果需要，开始tcc事务前提前准备一些各服务间所需的交互数据，这里localAction和remoteAction分属不同事务<br>
因为写ROOT事务表需要和localAction是原子操作
<li>预留资源：try阶段直接扣款，`X_TCC_TRANSACTION`表记录tcc事务状态，各服务事务表和localAction一个事务完成</li>
<li>confirm: ROOT需要所有remote confirm成功；BRANCH通过查找tccTxId实现幂等</li>
<li>cancel: 基本同confirm，且只有tccTxId存在且<b>状态不为confirming</b>才需要执行cancel</li>
<li>定时任务：使用shedlock保证定时任务只有一个执行，服务名作为锁名</li>
<li>定时任务：对try和cancel状态的`x_tcc_transaction`进行cancel操作</li>
<li>定时任务：对confirm状态的`x_tcc_transaction`进行confirm操作</li>
<li>若接口不幂等，TCCTransactionManager可以锁tcc事务记录实现单次补偿</li>
<li>进程存在多个replica时，可开启延迟recoveryJob，错开执行</li>
<li>此demo: order、account表不单独记录状态，因此confirm直接删除当前`x_tcc_transaction`</li>
<li>try存在数据删除时，可以的一种方式：try软删除，confirm删除，cancel恢复；有更新操作可select for update+判断状态合法性</li>
