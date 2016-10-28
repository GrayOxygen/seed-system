	  var BD_CONSTANT = {
	            DATA_TABLES : {
	                DEFAULT_OPTION : { //DataTables初始化选项
	                    language: {
	                        "sProcessing":   "处理中...",
	                        "sLengthMenu":   "每页 _MENU_ 项",
	                        "sZeroRecords":  "没有匹配结果",
	                        "sInfo":         "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
	                        "sInfoEmpty":    "当前显示第 0 至 0 项，共 0 项",
	                        "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	                        "sInfoPostFix":  "",
	                        "sSearch":       "搜索:",
	                        "sUrl":          "",
	                        "sEmptyTable":     "表中数据为空",
	                        "sLoadingRecords": "载入中...",
	                        "sInfoThousands":  ",",
	                        "oPaginate": {
	                            "sFirst":    "首页",
	                            "sPrevious": "上页",
	                            "sNext":     "下页",
	                            "sLast":     "末页",
	                            "sJump":     "跳转"
	                        },
	                        "oAria": {
	                            "sSortAscending":  ": 以升序排列此列",
	                            "sSortDescending": ": 以降序排列此列"
	                        }
	                    },
	                    autoWidth: false,   //禁用自动调整列宽
	                    stripeClasses: ["odd", "even"],//为奇偶行加上样式，兼容不支持CSS伪类的场合
	                    order: [],          //取消默认排序查询,否则复选框一列会出现小箭头
	                    processing: false,  //隐藏加载提示,自行处理
	                    serverSide: true,   //启用服务器端分页
	                    searching: false    //禁用原生搜索
	                },
	                COLUMN: {
	                    CHECKBOX: { //复选框单元格
	                        className: "td-checkbox",
	                        orderable: false,
	                        width: "30px",
	                        data: null,
	                        render: function (data, type, row, meta) {
		                            return   "<td width='10px'><input type='checkbox' name='id' value='" + data.id + "'></td>";
	                        }
	                    }
	                },
	                RENDER: {   //常用render可以抽取出来，如日期时间、头像等
	                    ELLIPSIS: function (data, type, row, meta) {
	                        data = data||"";
	                        return '<span title="' + data + '">' + data + '</span>';
	                    }
	                }
	            }
	    };
	  
	  var userManage = {
	    	    currentItem : null,
	    	    pageSortFormData : function(data) {
	    	        var param = new FormData();
	    	        //组装排序参数
	    	        if (data.order&&data.order.length&&data.order[0]) {
	    	        	for(var i=0;i<data.order.length;i++){
			    	        param.append("sort", data.columns[data.order[i].column].data+","+data.order[i].dir);
	    	        	}
	    	        }
	    	        //组装分页参数
	     	   	    param.append("page", data.start/data.length);
		   	        param.append("size",  data.length);
	    	 		param.append("draw",data.draw);
	    	        return param;
	    	    },
	    		deleteItem : function(delUrl,itemArray,urlAfterSuc){
	    			if(itemArray&&itemArray.length){
	    				if(itemArray.length==1){
	    					$("#msgID , .modal-body").text("确定删除"+itemArray[0].userName+"吗？");
	    				}
	    				if(itemArray.length>1){
	    					$("#msgID , .modal-body").text("确定删除"+itemArray.length+"项记录吗？");
	    				}
	   					openConfirm(function(){
	       					 $.ajax({
	       							url: delUrl + itemArray.map(function(x){return x.id}).join(),
	       							success:function(result){
	       								if(result&&result.success==true){
	       									window.location.href=urlAfterSuc ; 
	       								}else{
	       									openAlert(result.message);
	       								}
	       							}
	       						})
	       			      });
	    			}else{
	    				openAlert("请先选中操作的记录");
	    			}
	    		}
	    }
	    
