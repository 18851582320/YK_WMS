var fillObj=null;
var len=0;
/**
 * 大写键是否锁定 
 * @type Boolean true：表示当前为小写状态  false：表示当前为大写状态
 */
var isCapsLock=true;

function setFillObj(obj)
{
	fillObj = obj;
	fillObj.value = obj.value;
	document.getElementById("editKeyBoard").style.display='block';
	var str=obj.value;
	len=str.length;
	document.getElementById("keyboard").style.display='block';
	document.getElementById("keyboardcont").style.display='block';
}
/**
 * 设置大小写输入状态
 */
function setCapsLockStatus()
{
	if(isCapsLock)
	{
		isCapsLock = false;
		document.getElementById("capsLock").className="caps_on";
	}
	else
	{
		isCapsLock = true;	
		document.getElementById("capsLock").className="caps";
	}
}
/**
 * 填充空格
 */
function fillBlankspace()
{
	fillObj.value = fillObj.value+" ";
	len++;
}
/**
 * 删除输入的信息
 */
function delFillObj()
{
	fillObj.value = fillObj.value.substr(0,len-1);
	len--;
}
/**
 * 将输入的值填写到对应的输入框中
 * @param {} fillValue 
 */
function fillValue(fillValue)
{
	var reg = /[_a-zA-Z]/;
	if(reg.test(fillValue))
	{ 
		if(isCapsLock)
		{
			//小写输入
			fillObj.value = fillObj.value + fillValue.toLowerCase();
		}
		else
		{
			//大写输入
			fillObj.value = fillObj.value + fillValue.toUpperCase();
		}
	}
	else
		fillObj.value = fillObj.value + fillValue;
	len++;
	if(fillValue=="00")
		len++;
}
/**
 * 确认按钮
 */
function cancelFillObj()
{
	fillObj == null;
	len = 0;
	document.getElementById("keyboard").style.display='none';
	document.getElementById("keyboardcont").style.display='none';
}
/**
 * 关闭软键盘
 */
function closeKeyBoard()
{
	document.getElementById("keyboard").style.display='none';
	document.getElementById("keyboardcont").style.display='none';
}