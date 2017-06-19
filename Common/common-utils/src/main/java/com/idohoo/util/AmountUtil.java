/**
 * 
 */
package com.idohoo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idohoo.constants.IConstants;



/**
 * 金额计算工具类
 * @author Administrator
 *
 */
public class AmountUtil {

	  //格式化保留两位数
	  private DecimalFormat df_two = new DecimalFormat("#0.00");
	  //格式化保留4位数
	  private DecimalFormat df_four = new DecimalFormat("#0.0000");
	  //日期格式化
	  private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	  //月利率
	  private double monthRate = 0;
	  //月还利息
	  private double monPayRate = 0;
	  //月还本金
	  private double monPayAmount = 0;
	  //到期还款本息总额
	  private double totalAmount = 0;
	  //月还本息
	  private double totalSum = 0;
	  //本金余额
	  private double principalBalance = 0;
	  //利息余额
	  private double interestBalance = 0;
	  //总利息
	  private double totalInterest = 0;
	  //当前时间
	  private Date currTime;
	  //剩余本金
	  private double stillAmount = 0;
	  //月还款
	  private double monPay = 0;
	  //所借本金
	  private double amount = 0;
	  //本息余额
	  private double payRemain = 0;
	  //本金
	  private double payAmount = 0;
	  //日息
	  private double dayAmount=0;
	  //投资管理费
	  private double iManageFee = 0;
	  //投资收益总额
	  private double earnAmount = 0;
	  //奖励
	  private double rewardSum = 0;
	  //获取日期
	  private Date date = new Date();
	  //推荐好友注册投标奖励总金额
	  private double recfSum=0;
	  //推荐好友注册投标奖励每期还款金额（按月）
	  private double eachRepRecf=0;
	  //推荐好友注册投标奖励最后一期还款金额（按月）
	  private double lastRepRecf=0;
	  
	  //返回的结果集
	  private List<Map<String,Object>> mapList = null;
	  //map记录
	  private Map<String,Object> map = null;
	  
	  
	/**   
	 * @MethodName: add  
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午05:15:18
	 * @Return:    
	 * @Descb: 日期累加
	 * @Throws:
	*/
	public static Date add(Date date,int type,int value){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(type, value);
			return calendar.getTime();
	 }
	 
	/**
	 * 计算两个时间相差的日期 天数
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
	       try {
	    	   oDate=sdf.parse(sdf.format(oDate));
	    	   fDate=sdf.parse(sdf.format(fDate));  
	       } catch (ParseException e) {
	    	   e.printStackTrace();
	       }
	       Calendar cal = Calendar.getInstance();    
	       cal.setTime(fDate);    
	       long time1 = cal.getTimeInMillis();                 
	       cal.setTime(oDate);    
	       long time2 = cal.getTimeInMillis();         
	       long between_days=(time2-time1)/(1000*3600*24);  
	            
	       return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**   
	 * @MethodName: formatNumber  
	 * @Param: AmountUtil  
	 * @Author: gang.lv
	 * @Date: 2013-5-13 下午04:15:57
	 * @Return:    
	 * @Descb: 格式化输出为.0的数字
	 * @Throws:
	*/
	public static String formatNumber(String number){
		if(number.startsWith("."))
			return "0"+number;
		return number;
	}
	/**   
	 * @MethodName: rateSecondsSum  
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午09:42:34
	 * @Return:    
	 * @Descb: 秒还借款
	 * @Throws:
	*/
	public List<Map<String,Object>> rateSecondsSum(double borrowSum,double yearRate,int deadline){
		  mapList = new ArrayList<Map<String,Object>>();
		  map = new HashMap<String,Object>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  totalInterest = Double.valueOf(df_two.format(borrowSum*monthRate));
		  totalAmount = borrowSum + totalInterest;
		  map.put("repayPeriod", "1/1");
		  map.put("repayDate", sf.format(date));
		  map.put("stillPrincipal", df_two.format(borrowSum));
		  map.put("stillInterest", df_two.format(totalInterest));
		  map.put("principalBalance", 0);
		  map.put("interestBalance", 0);
		  map.put("mRate", df_four.format(monthRate*100));
		  map.put("totalSum", df_two.format(totalAmount));
		  map.put("totalAmount", df_two.format(totalAmount));
		  mapList.add(map);
		  return mapList;
	}
	
	
	/**   
	 * @MethodName: earnSecondsSum  
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午10:17:59
	 * @Return:    
	 * @Descb: 秒还还款收益
	 * @Throws:
	*/
	public Map<String,String> earnSecondsSum(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int viewMode,double oriAnnualRate,double reAnnualRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		monthRate = Double.valueOf(yearRate*0.01)/12.0;
		totalInterest = borrowSum*monthRate;
		totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
		totalInterest = Double.valueOf(df_four.format(totalInterest));
		iManageFee = totalInterest*0.1;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		rewardSum = excitationSum*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
		rewardSum = Double.valueOf(df_two.format(rewardSum));
		earnAmount = realAmount + totalInterest+rewardSum-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
		     /*msg.append("投资秒还借款月利率："+df_four.format(monthRate*100));
		     msg.append("%<br/>其中投资金额：￥"+realAmount+"元<br/>");
		     msg.append("收益利息：￥"+df_two.format(totalInterest)+"元<br/>");
		     msg.append("扣除投资管理费：￥"+iManageFee+"元<br/>");
		     msg.append("收益总额：￥"+earnAmount+"元");*/
			
		     msg.append(earnAmount+"元");
		}else{
			/*if(reAnnualRate>0){
				msg.append("投标"+realAmount+"元,年利率："+oriAnnualRate+"%+奖"+reAnnualRate);
			}else{
				msg.append("投标"+realAmount+"元,年利率："+yearRate);
			}
		    msg.append("%,期限"+deadline+"个月,可获得利息收益：￥"+df_two.format(totalInterest)+"元");*/
			
			msg.append(df_two.format(totalInterest)+"元");
		}
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	/**   
	 * @MethodName: rateCalculateMonth
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费  
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额
	 * 
	 * yts 20141218 修改：添加 recfScale
	 * recfSacle  推荐好友注册投标奖励年化利率  计算方式两种
	 * 天：投标金额*借款期限*(奖励利率/100)/360
	         月：投标金额*借款期限*(奖励利率/100)/12
	 * 
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午09:01:34
	 * @Return:    
	 * @Descb: 按月等额还款
	 * @Throws:
	*/
	public List<Map<String,Object>> rateCalculateMonth(double borrowSum,double yearRate,int deadline,int isDayThe,double recfScale){
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  //月标
			   monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
			   monPay = Double.valueOf(df_two.format(monPay));
			   amount = borrowSum;
			   monPayRate = 0;
			   monPayAmount = 0;
			   totalAmount = monPay*deadline;
			   payRemain = Double.valueOf(df_two.format(totalAmount));
			   //推荐好友注册投标奖励
			   recfSum=borrowSum*deadline*recfScale/12/100;
			   recfSum=Double.valueOf(df_two.format(recfSum));
			   eachRepRecf=Double.valueOf(df_two.format(recfSum/deadline));
			   lastRepRecf=Double.valueOf(df_two.format(recfSum-(eachRepRecf*(deadline-1))));
			   for(int n=1;n<=deadline;n++){
				   map = new HashMap<String,Object>();
				   currTime = add(date,Calendar.MONTH,n);
				   monPayRate = Double.valueOf(df_two.format(amount*monthRate));
				   monPayAmount = Double.valueOf(df_two.format(monPay-monPayRate));
				   amount = Double.valueOf(df_two.format(amount-monPayAmount));
				   
				   if(n == deadline){
					   monPay =payRemain;
					   monPayAmount = borrowSum - payAmount;
					   monPayRate = monPay - monPayAmount;
				   }
				   payAmount += monPayAmount;
				   payRemain = Double.valueOf(df_two.format(payRemain-monPay));
				   principalBalance = amount;
				   interestBalance = Double.valueOf(df_two.format(payRemain - principalBalance));
				   if(n == deadline){
					   payRemain = 0;
					   principalBalance = 0;
					   interestBalance = 0;
				   }
				   totalSum =monPayAmount +monPayRate;
				   map.put("repayPeriod", n+"/"+deadline);
				   map.put("repayDate", sf.format(currTime));
				   map.put("stillPrincipal", df_two.format(monPayAmount));
			       map.put("principalBalance", df_two.format(principalBalance));
				   map.put("interestBalance", df_two.format(interestBalance));
				   map.put("stillInterest", df_two.format(monPayRate));
				   map.put("mRate", df_four.format(monthRate*100));
				   map.put("totalSum", df_two.format(totalSum));
				   map.put("totalAmount", df_two.format(totalAmount));
				   
				   map.put("recivedRecfSum",n!=deadline?eachRepRecf:lastRepRecf );
				   map.put("hasRecfSum", 0); //一次性返回推荐好友投标奖励
				   mapList.add(map); 
			   }
		  }else{           
			  //天标  （目前系统还款方式没有按月等额还款）
			  map = new HashMap<String,Object>();
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }
		return mapList;
	}
	
	
	/**   
	 * @MethodName: earnCalculateMonth
	 *  msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费  
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午11:07:46
	 * @Return:    
	 * @Descb: 按月等额还款收益
	 * @Throws:
	*/
	public Map<String,String> earnCalculateMonth(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double manageFeeRate,double oriAnnualRate,double reAnnualRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		monthRate = (yearRate*0.01)/12;
		if(isDayThe == 1){
			  //月标
			monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
			monPay = Double.valueOf(df_two.format(monPay));
			amount = borrowSum;
			monPayRate = 0;
			monPayAmount = 0;
			totalAmount = monPay*deadline;
			payRemain = Double.valueOf(df_two.format(totalAmount));
			for(int n=1;n<=deadline;n++){
				map = new HashMap<String,Object>();
				currTime = add(date,Calendar.MONTH,n);
				monPayRate = Double.valueOf(df_two.format(amount*monthRate));
				monPayAmount = Double.valueOf(df_two.format(monPay-monPayRate));
				amount = Double.valueOf(df_two.format(amount-monPayAmount));
				   
				if(n == deadline){
					monPay =payRemain;
					monPayAmount = borrowSum - payAmount;
					monPayRate = monPay - monPayAmount;
				}
				payAmount += monPayAmount;
				payRemain = Double.valueOf(df_two.format(payRemain-monPay));
				if(n == deadline){
				   payRemain = 0;
				}
				totalInterest = totalInterest + monPayRate;
			}  
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m="个月";
		  }else{
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m= "天";
		  }
		iManageFee = totalInterest*manageFeeRate;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
		    msg.append("投资期数"+deadline+m+",月利率："+df_four.format(monthRate*100)+"%<br/>");
		    msg.append("投资金额：￥"+realAmount+"元<br/>到期收益利息：￥"+df_two.format(totalInterest)+"元<br/>");
		    msg.append("到期扣除投资管理费：￥"+iManageFee+"元<br/>");
		    msg.append("到期收益总额：￥"+earnAmount+"元");
		}else{
			if(reAnnualRate>0){
				msg.append("投标"+realAmount+"元,年利率："+oriAnnualRate+"%+奖"+reAnnualRate);
			}else{
				msg.append("投标"+realAmount+"元,年利率："+yearRate);
			}
		    msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		}
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/**   
	 * @MethodName: rateCalculateSum
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额  
	 * 
	 * 	 * yts 20141218 修改：添加 recfScale
	 * recfSacle  推荐好友注册投标奖励年化利率  计算方式两种
	 * 天：投标金额*借款期限*(奖励利率/100)/360
	         月：投标金额*借款期限*(奖励利率/100)/12
	 * 
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午06:21:20
	 * @Return:
	 * @Descb: 按月先息后本
	 * @Throws:
	*/
	public List<Map<String,Object>> rateCalculateSum(double borrowSum,double yearRate,int deadline,int isDayThe,double recfScale,Date in_date){
		date=in_date;
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = (yearRate*0.01)/12;
		  if(isDayThe == 1){
			  //月标
			  if(deadline>60){
				  return null;
			  }
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/12));
			  interestBalance = totalInterest;
			  totalAmount = borrowSum + totalInterest;
			   //推荐好友注册投标奖励
			   recfSum=borrowSum*deadline*recfScale/12/100;
			   recfSum=Double.valueOf(df_two.format(recfSum));
			   eachRepRecf=Double.valueOf(df_two.format(recfSum/deadline));
			   lastRepRecf=Double.valueOf(df_two.format(recfSum-(eachRepRecf*(deadline-1))));
			  for(int n = 1;n<=deadline;n++){
				  map = new HashMap<String,Object>();
				  currTime = add(date,Calendar.MONTH,n);
				  if(n == deadline){
					  stillAmount = borrowSum;
					  map.put("stillPrincipal", df_two.format(borrowSum));
					  map.put("principalBalance", 0);
					  map.put("interestBalance", 0);
					  map.put("stillInterest", df_two.format(totalInterest-monPayRate*(deadline-1)));
				  }else{
					  interestBalance = interestBalance- monPayRate;
			          map.put("stillPrincipal", 0);
			          map.put("principalBalance", df_two.format(borrowSum));
					  map.put("interestBalance", df_two.format(interestBalance));
					  map.put("stillInterest", df_two.format(monPayRate));
				  }
				  totalSum = stillAmount + monPayRate;		  
				  map.put("repayPeriod", n+"/"+deadline);
				  map.put("repayDate", sf.format(currTime));
				  
				  map.put("mRate", df_four.format(monthRate*100));
				  map.put("totalSum", df_two.format(totalSum));
				  map.put("totalAmount", df_two.format(totalAmount));
				  
				   map.put("recivedRecfSum",n!=deadline?eachRepRecf:lastRepRecf );
				   map.put("hasRecfSum", 0); //一次性返回推荐好友投标奖励
				  mapList.add(map);
			  }
		  }else{
			  map = new HashMap<String,Object>();
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }
		  return mapList;
	  }
	
	/**   
	 * @MethodName: earnCalculateSum
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费  
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-21 上午12:12:04
	 * @Return:    
	 * @Descb: 按月先息后本收益
	 * @Throws:
	*/
	public Map<String,String> earnCalculateSum(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double oriAnnualRate,double reAnnualRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		monthRate = Double.valueOf(yearRate*0.01)/12.0;
		monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
		if(isDayThe == 1){
			  //月标
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/12));
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m="个月";
		  }else{
			  //天标
			  totalInterest = (monPayRate*(Double.valueOf(realAmount)/Double.valueOf(borrowSum)))/30.0;
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m= "天";
		  }
		iManageFee = totalInterest*0.1;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
			   msg.append("月利率："+df_four.format(monthRate*100)+"%,");
			   msg.append("投资期数"+deadline+m+"<br/>其中投资金额：￥"+realAmount+"元<br/>到期收益利息：￥"+df_two.format(totalInterest)+"元<br/>");
			   msg.append("到期扣除投资管理费：￥"+iManageFee+"元<br/>");
			   msg.append("到期收益总额：￥"+earnAmount+"元");
		    }else{
				if(reAnnualRate>0){
					msg.append("投标"+realAmount+"元,年利率："+oriAnnualRate+"%+奖"+reAnnualRate);
				}else{
					msg.append("投标"+realAmount+"元,年利率："+yearRate);
				}
		       msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		    }
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/**   
	 * @MethodName: rateCalculateOne
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额  
	 * 
	 * 	 * yts 20141218 修改：添加 recfScale
	 * recfSacle  推荐好友注册投标奖励年化利率  计算方式两种
	 * 天：投标金额*借款期限*(奖励利率/100)/360
	         月：投标金额*借款期限*(奖励利率/100)/12
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-20 下午09:54:20
	 * @Return:    
	 * @Descb: 一次性还款
	 * @Throws:
	*/
	public List<Map<String,Object>> rateCalculateOne(double borrowSum,double yearRate,int deadline,int isDayThe,double recfScale){
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
			//推荐好友注册投标奖励
			recfSum=isDayThe==1?(borrowSum*deadline*recfScale/12/100):(borrowSum*deadline*recfScale/360/100);
			recfSum=Double.valueOf(df_two.format(recfSum));
		  if(isDayThe == 1){
			  //月标
			  map = new HashMap<String,Object>();
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  //totalInterest = monPayRate*deadline;
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/12.0));
			  totalAmount = borrowSum + totalInterest;
			  currTime = add(date,Calendar.MONTH,deadline);
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  
			  map.put("recivedRecfSum",0 );
			  map.put("hasRecfSum", recfSum); //一次性返回推荐好友投标奖励
			  mapList.add(map);
		  }else{
			  map = new HashMap<String,Object>();
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/360));
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  
			  map.put("recivedRecfSum",0 );
			  map.put("hasRecfSum", recfSum); //一次性返回推荐好友投标奖励
			  mapList.add(map);
		  }
		  return mapList;
	  }
	
	/**   
	 * @MethodName: earnCalculateOne  
	 * msg 收益消息
	 * realAmount 实际投资金额
	 * totalInterest 收益利息
	 * rewardSum 收益奖励 
	 * iManageFee 投资管理费 
	 * viewMode 显示模式 1 统计 2 展示
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Date: 2013-4-21 上午12:13:12
	 * @Return:    
	 * @Descb: 一次性还款收益
	 * @Throws:
	*/
	public Map<String,String> earnCalculateOne(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double manageFeeRate,double oriAnnualRate,double reAnnualRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		monthRate = Double.valueOf(yearRate*0.01)/12.0;
		monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
		if(isDayThe == 1){
			  //月标
			  totalInterest = monPayRate*deadline;
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_four.format(totalInterest));
			  m="个月";
		  }else{
			  //天标
			  totalInterest = Double.valueOf(df_two.format(borrowSum*yearRate*deadline*0.01/360));
			  totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
			  totalInterest = Double.valueOf(df_two.format(totalInterest));
			  m= "天";
		  }
		iManageFee = totalInterest * manageFeeRate;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
			   msg.append("月利率："+df_four.format(monthRate*100));
			   msg.append("%,投资期数"+deadline+m+"<br/>其中投资金额：￥"+realAmount+"元<br/>到期收益利息：￥"+df_two.format(totalInterest));
			   msg.append("元<br/>到期扣除投资管理费：￥"+iManageFee+"元<br/>");
			   msg.append("到期收益总额：￥"+earnAmount+"元");
		    }else{
				if(reAnnualRate>0){
					msg.append("投标"+realAmount+"元,年利率："+oriAnnualRate+"%+奖"+reAnnualRate);
				}else{
					msg.append("投标"+realAmount+"元,年利率："+yearRate);
				}
		       msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		    }
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	
	/**
	 *满标时先付利息，到期还本,只供天标使用
	 */
	public List<Map<String,Object>> rateCalculateOneIThenP(double borrowSum,double yearRate,int deadline,int isDayThe){
		
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  //月标
			  map = new HashMap<String,Object>();
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = monPayRate*deadline;
			  totalAmount = borrowSum + totalInterest;
			  currTime = add(date,Calendar.MONTH,deadline);
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }else{
			  map = new HashMap<String,Object>();
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/360));
			  currTime = add(date,Calendar.DATE,deadline);
			  totalAmount = borrowSum + totalInterest;
			  map.put("repayPeriod", "1/1");
			  map.put("repayDate", sf.format(currTime));
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("stillInterest", df_two.format(totalInterest));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(totalAmount));
			  map.put("totalAmount", df_two.format(totalAmount));
			  mapList.add(map);
		  }
		  return mapList;
	  }
	
	
	/**   
	 * 计算收益，先付利息，到期还本,只供天标使用
	*/
	public Map<String,String> earnCalculateIThenP(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double manageFeeRate,double oriAnnualRate,double reAnnualRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		//总利息
		totalInterest = Double.valueOf(df_two.format(borrowSum*yearRate*deadline*0.01/360));
		totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
		totalInterest = Double.valueOf(df_two.format(totalInterest));
		m= "天";
		//投资服务费
		iManageFee = totalInterest * manageFeeRate;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		//到期收益总额
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
			   msg.append("年利率："+df_four.format(yearRate));
			   msg.append("%,投资期限"+deadline+m+"<br/>其中投资金额：￥"+realAmount+"元<br/>到期收益总利息：￥"+df_two.format(totalInterest));
			   msg.append("元<br/>到期总共扣除投资管理费：￥"+iManageFee+"元<br/>");
			   msg.append("到期收益总额：￥"+earnAmount+"元");
		    }else{
				if(reAnnualRate>0){
					msg.append("投标"+realAmount+"元,年利率："+oriAnnualRate+"%+奖"+reAnnualRate);
				}else{
					msg.append("投标"+realAmount+"元,年利率："+yearRate);
				}
		       msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		    }
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/**
	 * （按天计息）按月付息，到期还本,只供天标使用
	 * 
	 * 	 * yts 20141218 修改：添加 recfScale
	 * recfSacle  推荐好友注册投标奖励年化利率  计算方式两种
	 * 天：投标金额*借款期限*(奖励利率/100)/360
	         月：投标金额*借款期限*(奖励利率/100)/12
	 */
	
	public List<Map<String,Object>> rateCalculateSumDay(double borrowSum,double yearRate,int deadline,int isDayThe,double recfScale){
		Date endDate = null;
		Date thisRepayDate=null;
		Date lastRepayDate=null;
		mapList = new ArrayList<Map<String,Object>>();
		monthRate = (yearRate*0.01)/12;

			//推荐好友注册投标奖励
			recfSum=isDayThe==1?(borrowSum*deadline*recfScale/12/100):(borrowSum*deadline*recfScale/360/100);
			recfSum=Double.valueOf(df_two.format(recfSum));

			
		  if(isDayThe == 1){
			  //月标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = monPayRate*deadline;
			  interestBalance = totalInterest;
			  
				eachRepRecf=Double.valueOf(df_two.format(recfSum/deadline));
				lastRepRecf=Double.valueOf(df_two.format(recfSum-(eachRepRecf*(deadline-1))));
				
			  for(int n = 1;n<=deadline;n++){
				  map = new HashMap<String,Object>();
				  currTime = add(date,Calendar.MONTH,n);
				  if(n == deadline){
					  stillAmount = borrowSum;
					  map.put("stillPrincipal", df_two.format(borrowSum));
					  map.put("principalBalance", 0);
					  map.put("interestBalance", 0);
				  }else{
					  interestBalance = interestBalance- monPayRate;
			          map.put("stillPrincipal", 0);
			          map.put("principalBalance", df_two.format(borrowSum));
					  map.put("interestBalance", df_two.format(interestBalance));
				  }
				  totalSum = stillAmount + monPayRate;
				  totalAmount = borrowSum + totalInterest;
				  map.put("repayPeriod", n+"/"+deadline);
				  map.put("repayDate", sf.format(currTime));
				  map.put("stillInterest", df_two.format(monPayRate));
				  map.put("mRate", df_four.format(monthRate*100));
				  map.put("totalSum", df_two.format(totalSum));
				  map.put("totalAmount", df_two.format(totalAmount));
				  
				   map.put("recivedRecfSum",n!=deadline?eachRepRecf:lastRepRecf );
				   map.put("hasRecfSum", 0); //一次性返回推荐好友投标奖励
				  mapList.add(map);
			  }
		  }else{
			  //天标
			  //最后一期的还款日期
			  endDate = add(date,Calendar.DATE,deadline);
			  //天标总利息
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/360));
			  //剩余利息
			  interestBalance = totalInterest;
			  
			  //还款总额
			  totalAmount = borrowSum + totalInterest;
			  
			  //上期还款时间
			  lastRepayDate=date;
			  //本期还款时间
			  thisRepayDate = add(lastRepayDate,Calendar.MONTH,1);
			  //本期天数
			  long days=0;
			  //本期利息
			  double thisInterest = 0;
			  
			  //因为满标审核时，推荐好友注册投标注册每期要还的金额计算方式为 期数(deadline/30 向上取整) ，so这也要跟满标审核一致  add yts 20141218
			  eachRepRecf=Double.valueOf(df_two.format(recfSum/Math.ceil(deadline/(float)30)));
			  int n=0;
			  
			  while(thisRepayDate.getTime()<endDate.getTime()){
				  n++;
				  map = new HashMap<String,Object>();
				  
				  days=(thisRepayDate.getTime()-lastRepayDate.getTime())/(24*60*60*1000);
				  //本期利息
				  thisInterest=Double.valueOf(df_two.format(totalInterest*days/deadline));

				  
				  //剩余利息
				  interestBalance = interestBalance- thisInterest;
		          map.put("stillPrincipal", 0);
		          map.put("principalBalance", df_two.format(borrowSum));
				  map.put("interestBalance", df_two.format(interestBalance));
				  //本息余额
				  totalSum = interestBalance+0;
				  map.put("repayDate", sf.format(thisRepayDate));
				  map.put("stillInterest", df_two.format(thisInterest));
				  map.put("mRate", df_four.format(monthRate*100));
				  map.put("totalSum", df_two.format(totalSum));
				  map.put("totalAmount", df_two.format(totalAmount));
				  
				  map.put("recivedRecfSum",eachRepRecf);
				  map.put("hasRecfSum", 0);
				  mapList.add(map);
				  lastRepayDate=thisRepayDate;
				  thisRepayDate = add(thisRepayDate,Calendar.MONTH,1);
			  }
			  map = new HashMap<String,Object>();
			  //最后一期
			  lastRepRecf=Double.valueOf(df_two.format(recfSum-(eachRepRecf*n)));
			  
			  lastRepayDate=thisRepayDate;
			  thisRepayDate=endDate;
			  map.put("stillPrincipal", df_two.format(borrowSum));
			  map.put("principalBalance", 0);
			  map.put("interestBalance", 0);
			  map.put("repayDate", sf.format(thisRepayDate));
			  map.put("stillInterest", df_two.format(interestBalance));
			  map.put("mRate", df_four.format(monthRate*100));
			  map.put("totalSum", df_two.format(0));
			  map.put("totalAmount", df_two.format(totalAmount));
			  
			  map.put("recivedRecfSum",lastRepRecf);
			  map.put("hasRecfSum", 0);
			  mapList.add(map);
			  int num=1;
			  for(Map<String,Object> map : mapList){
				  map.put("repayPeriod", num+"/"+mapList.size());
				  num++;
			  }
			  
		  }
		  return mapList;
	  }
	
	/**   
	 * 计算收益，天标按月付息，到期还本
	*/
	public Map<String,String> earnCalculateSumDay(double realAmount,double borrowSum,double yearRate,int deadline,double excitationSum,int isDayThe,int viewMode,double manageFeeRate,double oriAnnualRate,double reAnnualRate){
		Map<String,String> mapEarn = new HashMap<String, String>();
		StringBuffer msg = new StringBuffer();
		double totalInterest = 0;
		String m = "";
		//总利息
		totalInterest = Double.valueOf(df_two.format(borrowSum*yearRate*deadline*0.01/360));
		totalInterest = totalInterest*(Double.valueOf(realAmount)/Double.valueOf(borrowSum));
		totalInterest = Double.valueOf(df_two.format(totalInterest));
		m= "天";
		//投资服务费
		iManageFee = totalInterest * manageFeeRate;
		iManageFee = Double.valueOf(df_two.format(iManageFee));
		//到期收益总额
		earnAmount = realAmount + totalInterest-iManageFee;
		earnAmount = Double.valueOf(df_two.format(earnAmount));
		if(viewMode == 1){
			   msg.append("年利率："+df_four.format(yearRate));
			   msg.append("%,投资期限"+deadline+m+"<br/>其中投资金额：￥"+realAmount+"元<br/>到期收益总利息：￥"+df_two.format(totalInterest));
			   msg.append("元<br/>到期总共扣除投资管理费：￥"+iManageFee+"元<br/>");
			   msg.append("到期收益总额：￥"+earnAmount+"元");
		    }else{
				if(reAnnualRate>0){
					msg.append("投标"+realAmount+"元,年利率："+oriAnnualRate+"%+奖"+reAnnualRate);
				}else{
					msg.append("投标"+realAmount+"元,年利率："+yearRate);
				}
		       msg.append("%,期限"+deadline+m+",可获得利息收益：￥"+df_two.format(totalInterest)+"元");
		    }
		mapEarn.put("msg", msg.toString());
		mapEarn.put("realAmount", realAmount+"");
		mapEarn.put("totalInterest", totalInterest+"");
		mapEarn.put("rewardSum", rewardSum+"");
		mapEarn.put("iManageFee", iManageFee+"");
		mapEarn.put("monthRate", monthRate+"");
		return mapEarn;
	}
	
	/**
	 * 按月付息到期还本
	 * 版本2，可以指定固定还款日还款
	 * @param borrowSum 借款金额
	 * @param yearRate 年化利率
	 * @param deadline 期限
	 * @param isDayThe 1月标2天标
	 * @param recfScale 奖励年率
	 * @param inDate 满标审核日期
	 * @param isSpecifyPayDay 1指定还款日0不指定还款日
	 * @param countType 计息规则
	 * @return
	 */
	public List<Map<String, Object>> rateCalculateSum2(
			double borrowSum, double yearRate, int deadline, int isDayThe,
			double recfScale, Date inDate,int isSpecifyPayDay,int countType) {
		//起息日
		Date startDate=add(inDate,Calendar.DATE,1);
		Date endDate = null;
		Date thisRepayDate = null;
		Date lastRepayDate = null;
		mapList = new ArrayList<Map<String, Object>>();
		monthRate = (yearRate * 0.01) / 12;

		borrowSum = Double.valueOf(df_two.format(borrowSum));
		
		//总借款天数
		long allDays=1;
		// 最后一期的还款日期
		if(isDayThe==1){
			//月标
			//过高的期限，会使服务器运算过长的时间
			if(deadline>60){
				return null;
			}
			endDate = add(inDate, Calendar.MONTH, deadline);
			//总利息
			allDays = (endDate.getTime() - inDate.getTime())
				/ (24 * 60 * 60 * 1000);
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}else{
			//天标
			//过高的期限，会使服务器运算过长的时间
			if(deadline>1830){
				return null;
			}
			endDate = add(inDate, Calendar.DATE, deadline);
			allDays=deadline;
			// 天标总利息
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}
		
		// 推荐好友注册投标奖励
		recfSum = borrowSum * allDays * recfScale / countType / 100;
		recfSum = Double.valueOf(df_two.format(recfSum));
		double recfBalance=recfSum;
		double thisRecf=0;

		
		// 剩余利息
		interestBalance = totalInterest;

		// 还款总额
		totalAmount = borrowSum + totalInterest;

		// 上期还款时间
		lastRepayDate = inDate;

		// 固定还款日
		int payDay = IConstants.PayDay;

		//本期还款日。
		if(isSpecifyPayDay==1){
			//如果指定还款日，首期还款时间为起息日的下个月的还款日
			thisRepayDate=add(startDate,Calendar.MONTH,1);
			thisRepayDate.setDate(payDay);
		}else{
			//如果不指定还款日，首期还款时间为满标审核时间加一个月
			thisRepayDate=add(inDate,Calendar.MONTH,1);
		}
	

		// 本期天数
		long days = 0;
		// 本期利息
		double thisInterest = 0;

	
		int n = 0;

		while (thisRepayDate.getTime() < endDate.getTime()) {
			n++;
			map = new HashMap<String, Object>();

			days = (thisRepayDate.getTime() - lastRepayDate.getTime())
					/ (24 * 60 * 60 * 1000);
			// 本期利息
			thisInterest = Double.valueOf(df_two.format(totalInterest * days
					/ allDays));

			// 剩余利息
			interestBalance = interestBalance - thisInterest;

			map.put("stillPrincipal", 0);
			map.put("days", days);
			map.put("principalBalance", df_two.format(borrowSum));
			map.put("stillInterest", df_two.format(thisInterest));
			map.put("interestBalance", df_two.format(interestBalance));
			// 本息余额
			totalSum = interestBalance + 0;
			map.put("totalSum", df_two.format(totalSum));

			map.put("repayDate", sf.format(thisRepayDate));

			map.put("mRate", df_four.format(monthRate * 100));

			map.put("totalAmount", df_two.format(totalAmount));

			// 本期奖励
			thisRecf = Double.valueOf(df_two.format(recfSum * days
					/ allDays));
			recfBalance=recfBalance-thisRecf;
			map.put("recivedRecfSum", df_two.format(thisRecf));
			map.put("hasRecfSum", 0);
			mapList.add(map);
			lastRepayDate = thisRepayDate;
			thisRepayDate = add(thisRepayDate, Calendar.MONTH, 1);
		}
		map = new HashMap<String, Object>();
		
		thisRepayDate = endDate;
		days = (thisRepayDate.getTime() - lastRepayDate.getTime())
				/ (24 * 60 * 60 * 1000);
		map.put("stillPrincipal", df_two.format(borrowSum));
		map.put("principalBalance", 0);
		map.put("interestBalance", 0);
		map.put("repayDate", sf.format(thisRepayDate));
		map.put("stillInterest", df_two.format(interestBalance));
		map.put("days", days);
		map.put("mRate", df_four.format(monthRate * 100));
		map.put("totalSum", df_two.format(0));
		map.put("totalAmount", df_two.format(totalAmount));

		map.put("recivedRecfSum", df_two.format(recfBalance));
		map.put("hasRecfSum", 0);
		mapList.add(map);
		int num = 1;
		for (Map<String, Object> map : mapList) {
			map.put("repayPeriod", num + "/" + mapList.size());
			num++;
		}

		return mapList;
	}
	
	/**
	 * 等额本息，按月计息
	 * 版本2，正规的等额本息算法
	 * @param borrowSum 借款金额
	 * @param yearRate 年利率
	 * @param deadline 期限
	 * @param isDayThe 1月标2天标
	 * @param recfScale 奖励年利率
	 * @param inDate 满标审核时间
	 * @return
	 */
	public List<Map<String,Object>> rateCalculateMonth2(double borrowSum,double yearRate,int deadline,int isDayThe,double recfScale,Date inDate){
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  if(deadline>60){
				  return null;
			  }
			  //月标
			  borrowSum = Double.valueOf(df_two.format(borrowSum));
			  //每月还本息
			  monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
			  monPay = Double.valueOf(df_two.format(monPay));
			  //总还款金额
			  totalAmount = monPay*deadline;
			  totalAmount=Double.valueOf(df_two.format(totalAmount));
			  //剩余本金
			  principalBalance=borrowSum;
			  //剩余利息
			  interestBalance=totalAmount-borrowSum;
		   monPayRate = 0;
		   monPayAmount = 0;
		   
		   payRemain = Double.valueOf(df_two.format(totalAmount));
		   //推荐好友注册投标奖励
		   recfSum=borrowSum*deadline*recfScale/12/100;
		   recfSum=Double.valueOf(df_two.format(recfSum));
		   eachRepRecf=Double.valueOf(df_two.format(recfSum/deadline));
		   lastRepRecf=Double.valueOf(df_two.format(recfSum-(eachRepRecf*(deadline-1))));
		   for(int n=1;n<=deadline;n++){
			   map = new HashMap<String,Object>();
			   currTime = add(inDate,Calendar.MONTH,n);
			   
			   
			   if(n == deadline){
				   monPayAmount = principalBalance;
				   monPayRate = interestBalance;
				   principalBalance = 0;
				   interestBalance = 0;
			   }else{
				 //本期利息
				   monPayRate = Double.valueOf(df_two.format(principalBalance*monthRate));
				   //本期本金
				   monPayAmount = Double.valueOf(df_two.format(monPay-monPayRate));
				   principalBalance = Double.valueOf(df_two.format(principalBalance-monPayAmount));
				   interestBalance=interestBalance-monPayRate;
			   }
			   
			   totalSum =monPayAmount +monPayRate;
			   map.put("repayPeriod", n+"/"+deadline);
			   map.put("repayDate", sf.format(currTime));
			   
			   map.put("stillPrincipal", df_two.format(monPayAmount));
			   map.put("principalBalance", df_two.format(principalBalance));
			   map.put("interestBalance", df_two.format(interestBalance));
			   map.put("stillInterest", df_two.format(monPayRate));
			   map.put("totalSum", df_two.format(totalSum));
			   
			   map.put("mRate", df_four.format(monthRate*100));
			   map.put("totalAmount", df_two.format(totalAmount));
			   
			   map.put("recivedRecfSum",n!=deadline?eachRepRecf:lastRepRecf );
			   map.put("hasRecfSum", 0); //一次性返回推荐好友投标奖励
				   mapList.add(map); 
			   }
		  }else{           
			  mapList=null;
		  }
		return mapList;
	}
	
	/**
	 * 等额本息，按天计息。
	 * 版本3，实现了按天计息的等额本息算法，目前不启用
	 * @param borrowSum 借款金额
	 * @param yearRate 年化利率
	 * @param deadline 期限，表示多少天或者多少个月
	 * @param isDayThe 1月标，2天标
	 * @param recfScale 奖励年利率
	 * @param inDate 满标审核时间
	 * @return
	 */
	public List<Map<String, Object>> rateCalculateMonth3(double borrowSum,
			double yearRate, int deadline, int isDayThe, double recfScale,Date inDate){
		try{
			Date startDate=add(inDate,Calendar.DATE,1);
			mapList = new ArrayList<Map<String, Object>>();
			borrowSum=Double.valueOf(df_two.format(borrowSum));
			//最后一期还款日
			Date endDate = null;
			//本期还款日
			Date thisRepayDate = null;
			//上一期还款日
			Date lastRepayDate = inDate;
			//月利率
			monthRate = (yearRate * 0.01) / 12;
			//天利率
			double dayFeeRate=yearRate/360.0/100;
			
			// 固定还款日
			int payDay = IConstants.PayDay;
			
			//本期还款日。
			thisRepayDate=add(startDate,Calendar.MONTH,1);
			thisRepayDate.setDate(payDay);
			
			
			// 本期天数
			long days = 0;
			// 本期利息
			double thisInterest = 0;
			//本期本金
			double thisPrincipal=0;
			// 最后一期的还款日期
			if(isDayThe==1){
				//月标
				endDate = add(inDate, Calendar.MONTH, deadline);
				//防止用户输入过高的期限，导致服务器运算时间过长
				if(deadline>60){
					return null;
				}
			}else{
				//天标                        
				endDate = add(inDate, Calendar.DATE, deadline);
				//防止用户输入过高的期限，导致服务器运算时间过长
				if(deadline>1830){
					return null;
				}
			}
			
			//算出每一期的还款日和计息天数
			while(thisRepayDate.getTime()<endDate.getTime()){
				map = new HashMap<String,Object>();
				map.put("repayDate", sf.format(thisRepayDate));
				days=(thisRepayDate.getTime()-lastRepayDate.getTime())/(24*60*60*1000);
				map.put("days",days);
				mapList.add(map);
				lastRepayDate=thisRepayDate;
				thisRepayDate=add(thisRepayDate,Calendar.MONTH,1);
			}
			map = new HashMap<String, Object>();
			map.put("repayDate", sf.format(endDate));
			days=(endDate.getTime()-lastRepayDate.getTime())/(24*60*60*1000);
			map.put("days",days);
			mapList.add(map);
			
			//计算每期应还本息，公式推导过程参照正常的等额本息还款计算公式的推导过程。
			double fz=borrowSum;
			double fm=0;
			int k=0;
			for(Map<String,Object> map:mapList){
				k++;
				fz=fz*(1+dayFeeRate*Double.valueOf(map.get("days")+""));
				if(k==1)continue;
				fm=(fm+1)*(1+dayFeeRate*Double.valueOf(map.get("days")+""));
			}
			fm=fm+1;
			//每期应还本息
			double x=fz/fm;
			x=Double.valueOf(df_two.format(x));
			int n = 0;
			
			// 还款总额
			totalAmount = x*(mapList.size());
			//利息余额
			interestBalance=totalAmount-borrowSum;
			totalSum=0;
			principalBalance=borrowSum;
			
			double thisRecf=0;
			for(Map<String,Object> map:mapList){
				n++;
				map.put("repayPeriod", n + "/" + mapList.size());
				thisInterest=principalBalance*dayFeeRate*Double.valueOf(map.get("days")+"");
				thisInterest=Double.valueOf(df_two.format(thisInterest));
				map.put("stillInterest", df_two.format(thisInterest));
				interestBalance=interestBalance-thisInterest;
				map.put("interestBalance", df_two.format(interestBalance));
				thisPrincipal=Double.valueOf(df_two.format(x-thisInterest));
				map.put("stillPrincipal", df_two.format(thisPrincipal));
				principalBalance=principalBalance-thisPrincipal;
				map.put("principalBalance", df_two.format(principalBalance));
	
				map.put("mRate", df_four.format(monthRate * 100));
				map.put("totalSum", df_two.format(totalSum));
				map.put("totalAmount", df_two.format(totalAmount));
	
				// 本期奖励,推荐好友投标奖励,在等额本息算法中是没法正确计算的。。
				thisRecf = Double.valueOf(df_two.format(thisInterest * recfScale
						/ yearRate));
				map.put("recivedRecfSum", df_two.format(thisRecf));
				map.put("hasRecfSum", 0); // 一次性返回推荐好友投标奖励
			}
			//由于每一期的还款金额是四舍五入的，导致末期的剩余本金不等于0，调整
			double mye=Double.valueOf(mapList.get(mapList.size()-1).get("principalBalance")+"");
			double mbj=Double.valueOf(mapList.get(mapList.size()-1).get("stillPrincipal")+"");
			mapList.get(mapList.size()-1).put("stillPrincipal", df_two.format(mbj+mye));
			mapList.get(mapList.size()-1).put("principalBalance",df_two.format(0));
			for(Map<String,Object> map:mapList){
				map.put("totalAmount", df_two.format(totalAmount+mye));
				map.put("interestBalance",  df_two.format(Double.valueOf(map.get("interestBalance")+"")+mye));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapList;
	}
	
	/**
	 * 一次性还本付息
	 * @param borrowSum 借款金额
	 * @param yearRate 年利率
	 * @param deadline 期限
	 * @param isDayThe 1月标2天标
	 * @param recfScale 奖励年利率
	 * @param inDate 满标审核日期
	 * @param countType 计息规则   360  365
	 * @return
	 */
	public List<Map<String,Object>> rateCalculateOne2(double borrowSum,double yearRate,int deadline,int isDayThe,double recfScale,Date inDate,int countType){
		Date endDate = null;
		mapList = new ArrayList<Map<String, Object>>();
		monthRate = (yearRate * 0.01) / 12;

		borrowSum = Double.valueOf(df_two.format(borrowSum));
		
		//总借款天数
		long allDays=1;
		// 最后一期的还款日期
		if(isDayThe==1){
			//月标
			//过高的期限，会使服务器运算过长的时间
			if(deadline>60){
				return null;
			}
			endDate = add(inDate, Calendar.MONTH, deadline);
			allDays = (endDate.getTime() - inDate.getTime())
				/ (24 * 60 * 60 * 1000);
			//总利息
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
			
		}else{
			//天标
			//过高的期限，会使服务器运算过长的时间
			if(deadline>1830){
				return null;
			}
			endDate = add(inDate, Calendar.DATE, deadline);
			allDays=deadline;
			// 天标总利息
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}
		
		// 推荐好友注册投标奖励
		recfSum = borrowSum * allDays * recfScale / countType / 100;
		recfSum = Double.valueOf(df_two.format(recfSum));
	
		// 剩余利息
		interestBalance = totalInterest;

		// 还款总额
		totalAmount = borrowSum + totalInterest;

		map = new HashMap<String,Object>();

		map.put("repayPeriod", "1/1");
		map.put("repayDate", sf.format(endDate));
		map.put("stillPrincipal", df_two.format(borrowSum));
		map.put("stillInterest", df_two.format(totalInterest));
		map.put("principalBalance", 0);
		map.put("interestBalance", 0);
		map.put("mRate", df_four.format(monthRate*100));
		map.put("totalSum", df_two.format(totalAmount));
		map.put("totalAmount", df_two.format(totalAmount));
		map.put("days", allDays);
		map.put("recivedRecfSum",recfSum );
		map.put("hasRecfSum", recfSum); //一次性返回推荐好友投标奖励
		mapList.add(map);
		return mapList;
	  }
	
	
	private static final int DFT_SCALE = 2;

	/** 大写数字 */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍",
	"陆", "柒", "捌", "玖" };
	/** 整数部分的单位 */
	private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰",
	"仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };
	/** 小数部分的单位 */
	private static final String[] DUNIT = { "角", "分", "厘" };

	/**
	* 得到大写金额。
	*/
	public static String toChinese(String str) {
	str = str.replaceAll(",", "");// 去掉","
	String integerStr;// 整数部分数字
	String decimalStr;// 小数部分数字

	// 初始化：分离整数部分和小数部分
	if (str.indexOf(".") > 0) {
	integerStr = str.substring(0, str.indexOf("."));
	decimalStr = str.substring(str.indexOf(".") + 1);
	} else if (str.indexOf(".") == 0) {
	integerStr = "";
	decimalStr = str.substring(1);
	} else {
	integerStr = str;
	decimalStr = "";
	}
	// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
	if (!integerStr.equals("")) {
	integerStr = Long.toString(Long.parseLong(integerStr));
	if (integerStr.equals("0")) {
	integerStr = "";
	}
	}
	// overflow超出处理能力，直接返回
	if (integerStr.length() > IUNIT.length) {
	System.out.println(str + ":超出处理能力");
	return str;
	}

	int[] integers = toArray(integerStr);// 整数部分数字
	boolean isMust5 = isMust5(integerStr);// 设置万单位
	int[] decimals = toArray(decimalStr);// 小数部分数字
	return getChineseInteger(integers, isMust5)
	+ getChineseDecimal(decimals);
	}

	/**
	* 整数部分和小数部分转换为数组，从高位至低位
	*/
	private static int[] toArray(String number) {
	int[] array = new int[number.length()];
	for (int i = 0; i < number.length(); i++) {
	array[i] = Integer.parseInt(number.substring(i, i + 1));
	}
	return array;
	}

	/**
	* 得到中文金额的整数部分。
	*/
	private static String getChineseInteger(int[] integers, boolean isMust5) {
	StringBuffer chineseInteger = new StringBuffer("");
	int length = integers.length;
	for (int i = 0; i < length; i++) {
	// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
	// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
	String key = "";
	if (integers[i] == 0) {
	if ((length - i) == 13)// 万(亿)(必填)
	key = IUNIT[4];
	else if ((length - i) == 9)// 亿(必填)
	key = IUNIT[8];
	else if ((length - i) == 5 && isMust5)// 万(不必填)
	key = IUNIT[4];
	else if ((length - i) == 1)// 元(必填)
	key = IUNIT[0];
	// 0遇非0时补零，不包含最后一位
	if ((length - i) > 1 && integers[i + 1] != 0)
	key += NUMBERS[0];
	}
	chineseInteger.append(integers[i] == 0 ? key
	: (NUMBERS[integers[i]] + IUNIT[length - i - 1]));
	}
	return chineseInteger.toString();
	}

	/**
	* 得到中文金额的小数部分。
	*/
	private static String getChineseDecimal(int[] decimals) {
	StringBuffer chineseDecimal = new StringBuffer("");
	for (int i = 0; i < decimals.length; i++) {
	// 舍去3位小数之后的
	if (i == 3)
	break;
	chineseDecimal.append(decimals[i] == 0 ? ""
	: (NUMBERS[decimals[i]] + DUNIT[i]));
	}
	return chineseDecimal.toString();
	}

	/**
	* 判断第5位数字的单位"万"是否应加。
	*/
	private static boolean isMust5(String integerStr) {
	int length = integerStr.length();
	if (length > 4) {
	String subInteger = "";
	if (length > 8) { // TODO 12-9-17
	// 取得从低位数，第5到第8位的字串
	subInteger = integerStr.substring(length - 8, length - 4);
	} else {
	subInteger = integerStr.substring(0, length - 4);
	}
	return Integer.parseInt(subInteger) > 0;
	} else {
	return false;
	}
	}

	/**
	* BigDecimal 相乘,四舍五入保留0位
	* 
	* @param a
	* @param b
	* @return a*b
	*/
	public static BigDecimal mutiply(String a, String b, int roundingMode) {
	BigDecimal bd = new BigDecimal(a);
	return bd.multiply(new BigDecimal(b)).setScale(DFT_SCALE, roundingMode);
	}

	/**
	* BigDecimal 相除,四舍五入保留两位
	* 
	* @param a
	* @param b
	* @return a/b
	*/
	public static BigDecimal div(String a, String b, int roundingMode) {
	BigDecimal decimal1 = new BigDecimal(a);
	BigDecimal decimal2 = new BigDecimal(b);
	return decimal1.divide(decimal2, DFT_SCALE, roundingMode);
	}

	/**
	* BigDecimal 相加,四舍五入保留两位
	* 
	* @param a
	* @param b
	* @return a+b
	*/
	public static BigDecimal sum(String a, String b, int roundingMode) {
	BigDecimal decimal1 = new BigDecimal(a);
	BigDecimal decimal2 = new BigDecimal(b);
	// DecimalFormat format = new DecimalFormat("#0.00");
	return decimal1.add(decimal2).setScale(DFT_SCALE, roundingMode);
	}

	/**
	* BigDecimal 相减,四舍五入保留两位
	* 
	* @param a
	* @param b
	* @return a+b
	*/
	public static BigDecimal sub(String a, String b, int roundingMode) {
	BigDecimal decimal1 = new BigDecimal(a);
	BigDecimal decimal2 = new BigDecimal(b);
	// DecimalFormat format = new DecimalFormat("#0.00");
	return decimal1.subtract(decimal2).setScale(DFT_SCALE, roundingMode);
	}

	/**
	* 100.00 为10000
	* 
	* @param a
	* @return
	*/
	public static BigDecimal format(String a, int roundingMode) {
	return new BigDecimal(a).multiply(new BigDecimal(100)).setScale(0,
	roundingMode);
	}

	/**
	 * 等额本息，按月计息
	 * 版本2，正规的等额本息算法
	 * @param borrowSum 借款金额
	 * @param yearRate 年利率
	 * @param deadline 期限
	 * @param isDayThe 1月标2天标
	 * @param recfScale 奖励年利率
	 * @param inDate 满标审核时间
	 * @return 利息总额
	 */
	public double rateCalculateMonthInterest(double borrowSum,double yearRate,int deadline,int isDayThe){
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  //月标
			  borrowSum = Double.valueOf(df_two.format(borrowSum));
			  //每月还本息
			  monPay = Double.valueOf(borrowSum*monthRate*Math.pow((1+monthRate),deadline))/Double.valueOf(Math.pow((1+monthRate),deadline) -1);
			  monPay = Double.valueOf(df_two.format(monPay));
			  //总还款金额
			  totalAmount = monPay*deadline;
			  totalAmount=Double.valueOf(df_two.format(totalAmount));
			  totalInterest=totalAmount-borrowSum;
		      BigDecimal bd = new BigDecimal(totalInterest);  
		      totalInterest=bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		  }
		return totalInterest;
	}
	
	/**
	 * 按月付息到期还本
	 * 版本2，可以指定固定还款日还款
	 * @param borrowSum 借款金额
	 * @param yearRate 年化利率
	 * @param deadline 期限
	 * @param isDayThe 1月标2天标
	 * @param recfScale 奖励年率
	 * @param inDate 满标审核日期
	 * @param isSpecifyPayDay 1指定还款日0不指定还款日
	 * @return 利息总额
	 */
	public double rateCalculateInterest2(
			double borrowSum, double yearRate, int deadline, int isDayThe,
			 Date inDate,int countType) {
		//起息日
		Date endDate = null;
		mapList = new ArrayList<Map<String, Object>>();
		monthRate = (yearRate * 0.01) / 12;

		borrowSum = Double.valueOf(df_two.format(borrowSum));
		
		//总借款天数
		long allDays=1;
		// 最后一期的还款日期
		if(isDayThe==1){
			//月标
			endDate = add(inDate, Calendar.MONTH, deadline);
			//总利息
			allDays = (endDate.getTime() - inDate.getTime())
				/ (24 * 60 * 60 * 1000);
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}else{
			//天标
			endDate = add(inDate, Calendar.DATE, deadline);
			allDays=deadline;
			// 天标总利息
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}
		
		return totalInterest;
	}
	
	/**   
	 * @MethodName: rateCalculateSum
	 * repayPeriod 还款期数
	 * repayDate   还款日期
	 * stillPrincipal  应还本金
	 * stillInterest   应还利息
	 * principalBalance 本金余额
	 * interestBalance  利息余额
	 * mRate   月利率
	 * totalSum  本息余额
	 * totalAmount  还款总额  
	 * 
	 * 	 * yts 20141218 修改：添加 recfScale
	 * recfSacle  推荐好友注册投标奖励年化利率  计算方式两种
	 * 天：投标金额*借款期限*(奖励利率/100)/360
	         月：投标金额*借款期限*(奖励利率/100)/12
	 * 
	 * @Param: AmountUtil  
	 * @Author: 
	 * @Return:
	 * @Descb: 按月先息后本
	 * @Throws:
	 * return 利息总额
	*/
	public double rateCalculateInterest(double borrowSum,double yearRate,int deadline,int isDayThe){
		  monthRate = (yearRate*0.01)/12;
		  if(isDayThe == 1){
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/12));
			  
		  }else{
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = (monPayRate*deadline)/30.0;
		  }
		  return totalInterest;
	  }
	
	/**
	 * 一次性还本付息
	 * @param borrowSum 借款金额
	 * @param yearRate 年利率
	 * @param deadline 期限
	 * @param isDayThe 1月标2天标
	 * @param recfScale 奖励年利率
	 * @param inDate 满标审核日期
	 * @return 利息总额
	 */
	public double rateCalculateOneInterest(double borrowSum,double yearRate,int deadline,int isDayThe,Date inDate,int countType){
		Date endDate = null;
		mapList = new ArrayList<Map<String, Object>>();
		monthRate = (yearRate * 0.01) / 12;

		borrowSum = Double.valueOf(df_two.format(borrowSum));
		
		//总借款天数
		long allDays=1;
		// 最后一期的还款日期
		if(isDayThe==1){
			//月标
			endDate = add(inDate, Calendar.MONTH, deadline);
			allDays = (endDate.getTime() - inDate.getTime())
				/ (24 * 60 * 60 * 1000);
			//总利息
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}else{
			//天标
			endDate = add(inDate, Calendar.DATE, deadline);
			allDays=deadline;
			// 天标总利息
			totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
					* (yearRate * 0.01) / countType));
		}
		
		return totalInterest;
	  }
	
	/**
	 *满标时先付利息，到期还本,只供天标使用
	 */
	public double rateCalculateOneIThenPInterest(double borrowSum,double yearRate,int deadline,int isDayThe){
		
		  mapList = new ArrayList<Map<String,Object>>();
		  monthRate = Double.valueOf(yearRate*0.01)/12.0;
		  if(isDayThe == 1){
			  //月标
			  map = new HashMap<String,Object>();
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = monPayRate*deadline;
		  }else{
			  //天标
			  monPayRate = Double.valueOf(df_two.format(borrowSum*monthRate));
			  totalInterest = Double.valueOf(df_two.format(borrowSum*deadline*(yearRate*0.01)/360));
			  
		  }
		  return totalInterest;
	  }
	/**
	 * 总利息  方式：按天计息
	 * @param borrowSum 借款总额
	 * @param yearRate 年利率
	 * @param deadline 期限 天标/天  月标/月
	 * @param isDayThe 是否天标
	 * @param inDate 起息日期
	 * @param countType 计息规则 360  365
	 * @return
	 */
	public double totalInterest(double borrowSum, double yearRate, int deadline, int isDayThe,
			Date inDate,int countType){
		Date endDate = null;
		borrowSum = Double.valueOf(df_two.format(borrowSum));
		//总借款天数
		long allDays=1;
		// 最后一期的还款日期
		if(isDayThe==1){
			endDate = add(inDate, Calendar.MONTH, deadline);
			allDays = (endDate.getTime() - inDate.getTime())
				/ (24 * 60 * 60 * 1000);
		}else{
			allDays=deadline;
		}
		totalInterest = Double.valueOf(df_two.format(borrowSum * allDays
				* (yearRate * 0.01) / countType));
		
		return totalInterest;
	}
	
	
	
	
	public static void main(String[] args) throws ParseException {
//		String number = "54452";
//		System.out.println(number + " " + AmountUtil.toChinese(number));
//		number = "30200";
//		System.out.println(number + " " + AmountUtil.toChinese(number));
//		number = "30000.05";
//		System.out.println(number + " " + AmountUtil.toChinese(number));
//		number = "300000000.00";
//		System.out.println(number + " " + AmountUtil.toChinese(number));
		
		AmountUtil amount=new AmountUtil();
//		double interestSum1=amount.rateCalculateMonthInterest(50000, 10, 3, 1);
//		double interestSum2=amount.rateCalculateInterest(50000, 10, 3, 1);
//		double interestSum4=amount.rateCalculateInterest2(50000, 10, 92, 2, (new Date()));
//		double interestSum3=amount.rateCalculateOneInterest(1, 1.08, 3, 1, (new Date()));
//		System.out.println("等额本息:"+interestSum1);
//		System.out.println("按月付息，到期还本:"+interestSum2);
//		System.out.println("按月付息，到期还本4:"+interestSum4);
//		System.out.println("一次性还本付息:"+interestSum3);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//起息日
		Date auditTime=AmountUtil.add(dateFormat.parse("2017-2-1 13:38:17"), Calendar.DATE, -1);
		long allDays=1;
		Date endDate = add(auditTime, Calendar.MONTH, 3);
		allDays = (endDate.getTime() - auditTime.getTime())
			/ (24 * 60 * 60 * 1000);
		System.out.println(dateFormat.format(endDate.getTime()));
		System.out.println(allDays);
	}
}