	function startDateClk(obj){
		var dateStr = "%y-%M-%d 00:00:00";
		if(jQuery.trim(obj.val()) != ""){
			dateStr = obj.val();
		}
		WdatePicker({startDate:dateStr,alwaysUseStartDate:true,readOnly:true,maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'});
	}
	function endDateClk(obj){
		var dateStr = "%y-%M-%d 23:59:59";
		if(jQuery.trim(obj.val()) != ""){
			dateStr = obj.val();
		}
		WdatePicker({startDate:dateStr,alwaysUseStartDate:true,readOnly:true,minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'});
	}
	