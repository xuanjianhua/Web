//存放主要交互逻辑js代码
//js模块化
var seckill = {
		//封装秒杀相关ajax的url
		URL: {
			now: function() {
				return "/shop/seckill/time/now";
			},
			expose:function(seckillId){
				return "/shop/seckill/"+seckillId+"/expose"
			},
			execution:function(seckillId,md5){
				return "/shop/seckill/"+seckillId+"/"+md5+"/excute"
			}
		},
		//验证手机号
		validatePhone: function(phone) {
			if (phone && phone.length == 11 && !isNaN(phone)) {
				return true;
			} else {
				return false;
			}
		},
		//执行秒杀
		handleSeckill:function(seckillId,node){
			//获取秒杀地址，控制秒杀逻辑，执行秒杀
			node.hide()
			.html("<button class='btn btn-primary btn-blg' id='startKillBtn'>开始秒杀</button>");
			$.post(seckill.URL.expose(seckillId),{},function(result){
				//在回调函数中，执行交互流程
				if(result&&result['success']){
					var exposer=result['data'];
					if(exposer['exposted']){
						//开始秒杀
						var md5=exposer['md5'];
						var killUrl=seckill.URL.execution(seckillId,md5);
						console.log('killUrl'+killUrl);
						//绑定一次点击事件
						$("#startKillBtn").one('click',function(){
							//执行秒杀请求
							//1 禁用按钮
							$(this).addClass('disabled');
							//2 发送秒杀请求执行秒杀
							$.post(killUrl,{},function(result){
								if(result&&result['success']){
									var killResult=result['data'];
									var state=killResult['state'];
									var stateInfo=killResult['stateInfo'];
									//显示秒杀结果
									node.html("<span class='label label-success'>"+stateInfo+"</span>");
								}
								
							});
							
						});
						node.show();	
					}else{
						//未开始秒杀
						var now=exposer['now'];
						var start=exposer['start'];
						var end=exposer['end'];
						seckill.mycountdown(seckillId,now,start,end);
					}
				}else{
					console.log('result'+result);
				}
			});
		},
		//倒计时
		mycountdown:function(seckillId,nowTime,startTime,endTime){
			var seckillBox=$("#seckill-box");
			//秒杀结束
			if(nowTime>endTime){
				seckillBox.html("秒杀结束");
			}else if(nowTime<startTime){//秒杀未开始
				var killTime=new Date(startTime+1000);
				//计时事件绑定
				seckillBox.countdown(killTime,function(event){
					//时间格式
					var format=event.strftime('秒杀倒计时：%D天  %H时  %M分  %S秒');
					seckillBox.html(format);
				}).on('finish.countdown',function(){//倒计时结束
					//获取秒杀地址，控制秒杀逻辑，执行秒杀
					seckill.handleSeckill(seckillId,seckillBox);
				});
			}else{
				//秒杀开始
				seckill.handleSeckill(seckillId,seckillBox);
			}
		},
		//详情页秒杀逻辑
			detail: {
			//详情页初始化
			init: function(params) {
				//手机验证和登录，计时交互
				//规划交互流程
				var killPhone = $.cookie('killPhone');
				//验证手机号
				if (!seckill.validatePhone(killPhone)) {
					//绑定phone
					var killPhoneModal = $('#killPhoneModal');
					killPhoneModal.modal({
						show: true, //显示弹出层
						backdrop: 'static', //禁止位置关闭
						keyboard: false //关闭键盘事件			
					});
					$('#killPhoneBtn').click(function() {
								var inputPhone = $('#killPhoneKey').val();
								if (seckill.validatePhone(killPhone)){
										//写入cookie
										$.cookie('killPhone', inputPhone, {
											expires: 7,
											path: '/seckill'
										});
										//刷新页面
										window.location.reload();
									} else {
										$('#killPhoneMessage').hide().html("<label class='label label-danger '>手机号错误！</label>").show(300);
									}
								});
						}
						//已登录
						//计时交互
					var startTime = params['startTime'];
					var endTime = params['endTime'];
					var seckillId = params['seckillId'];
					$.get(seckill.URL.now(), {}, function(result) {
						if (result && result['success']) {
							var nowTime = result['data'];
							seckill.mycountdown(seckillId,nowTime,startTime,endTime);
						} else {
							console.log('result' + result);
						}
					});
				}
			}
		}	