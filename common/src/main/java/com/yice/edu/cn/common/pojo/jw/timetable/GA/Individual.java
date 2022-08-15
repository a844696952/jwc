package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
* @ClassName: Individual  
* @Description: 遗传个体  
* @author xuchang  
* @date 2018年12月26日
 */
public class Individual {
	
	//以教室切片为维度构建二维染色体
	private Matrix chromosome;
	private double fitness = -1;
	
	
	public Individual() {
		// TODO Auto-generated constructor stub
	}
	
	public Individual(int y,int x) {
		
	}
	
	/**
	 * 
	 * @Description: 初始化个体  
	 * @param 课表资源对象
	 * @return  Individual  生成个体    
	 * @throws
	 */
	public static Individual initIndividual(ClassScheduleBo classSchedule ) {
		Individual ind=new Individual();
		ind.setChromosome(new Matrix(classSchedule.getTeacherTimes().size(), classSchedule.getClasses().size(), 0));
		// 随机个体
		// TODO 
		for(ClassesBo c:classSchedule.getClasses().values()) {
			//无法满足硬性条件冲突
			if(generateClassSequence(classSchedule, c.getId(), ind)) {
				return null;
			}
		}
		return ind;
	}
	
	public static boolean generateClassSequence(ClassScheduleBo classSchedule,int classesBoId,Individual ind ) {
		int[] copyIndx=new int[classSchedule.getTeacherTimes().size()];
		
		List<Integer> freeSlot=classSchedule.getTeacherTimes().values().stream().map(TeacherTime::getId).collect(Collectors.toList());
		//获取班级的教学计划
		List<TeachInfoSplit> freeList = new ArrayList<>(classSchedule.getTeachingPlanByClass().get(classesBoId));
		freeList.sort((tp1,tp2)->tp2.getNoArrangeSlotlen()-tp1.getNoArrangeSlotlen());
		
		//所有空间搜索完成是否有冲突
		AtomicBoolean whetherClash=new AtomicBoolean(false);
		IntStream.range(0, classSchedule.getTeachingPlanByClass().get(classesBoId).size()).forEach(teaInt->{
			//是否匹配到冲突
			boolean anyMatch=true;
			
//			int randomTeacherPlanIdex=(int)(freeList.size()*Math.random());
			TeachInfoSplit randomTeacherPlan=freeList.get(teaInt);
			List<Integer> noSearchSlot=null;
			//初始一个未搜索时间空间
			if(randomTeacherPlan.getNoArrangeSlotlen()>0) {
				Set<String> noArrangeSlot = randomTeacherPlan.getNoArrangeSlot();
				noSearchSlot=freeSlot.stream().filter(slot->!noArrangeSlot.contains(slot.toString())).collect(Collectors.toList());
			}else {
				noSearchSlot=new ArrayList<>(freeSlot);
			}
			
//			String teacherId1=randomTeacherPlan.getTeacherId();
			while(noSearchSlot.size()>0) {
				
				int randomIdex=(int)(noSearchSlot.size()*Math.random());
				Integer randomTimeId=noSearchSlot.remove(randomIdex);
				anyMatch= Individual.whetherTeacherClash(ind.getChromosome().M[randomTimeId-1], classSchedule, randomTeacherPlan);
//				anyMatch= Individual.whetherTeacherClash(ind.getChromosome().M[randomTimeId-1], classSchedule, teacherId1);
				
				//无冲突
				if(!anyMatch) {
					copyIndx[randomTimeId-1]=randomTeacherPlan.getId();
//					freeList.remove(randomTeacherPlan);
					freeSlot.remove(randomTimeId);
					break;
				}
				
			}
				
			if(anyMatch) {
				
				whetherClash.set(true);
				return;
			}
			
		});
		//如果没有冲突
		if(!whetherClash.get()) {
			for(int i=0;i<copyIndx.length;i++) {
				ind.setGeneValue(i, classesBoId-1,copyIndx[i] );
			}
		}
		
		return whetherClash.get();
	}

	

	public Matrix getChromosome() {
		return chromosome;
	}

	public void setChromosome(Matrix chromosome) {
		this.chromosome = chromosome;
	}

	public double getFitness() {
		return fitness;
	}


	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public int getGeneValue(int column ,int row) {
		
		return this.chromosome.M[column][row];
	}
	
	public void setGeneValue(int c,int r,int value) {
		
		this.chromosome.setM(c, r, value);
	}
	
	public boolean detectionClash(ClassScheduleBo classSchedule) {
		
		return Arrays.stream(this.chromosome.M).anyMatch(m->{
			
			return Arrays.stream(m).anyMatch(m2->{
				if(m2==0) {
					return false;
				}
				return Individual.whetherTeacherClash(m, classSchedule, m2);
			});
			
		});
		
		
	}
	
	public static boolean whetherTeacherClash(int[] rowArray,ClassScheduleBo classSchedule,TeachInfoSplit teachInfoSplit) {
		return Arrays.stream(rowArray).anyMatch(cross->{
			if(cross!=0&&teachInfoSplit.getId()!=cross) {
				
				TeachInfoSplit teachingPlan = classSchedule.getTeachingPlans().get(cross);
				String teacherId2=teachingPlan.getTeacherId();
				String[] split = teachInfoSplit.getTeacherId().split(",");
				String[] split2 = teacherId2.split(",");
				boolean clashValidation=Arrays.stream(split).anyMatch(s1->{
					return Arrays.stream(split2).anyMatch(s2->Objects.equals(s1, s2));
				});
				if(clashValidation) {
					return true;
				}
			}
			return false;
		});
		
		
	}
	
	public static boolean whetherTeacherClash(int[] rowArray,ClassScheduleBo classSchedule,int currentCross) {
		return Arrays.stream(rowArray).anyMatch(cross->{
			if(cross!=0&&currentCross!=cross) {
				TeachInfoSplit teachingPlan = classSchedule.getTeachingPlans().get(cross);
				String teacherId2=teachingPlan.getTeacherId();
				String[] split = classSchedule.getTeachInfoSplit(currentCross).getTeacherId().split(",");
				String[] split2 = teacherId2.split(",");
				boolean clashValidation=Arrays.stream(split).anyMatch(s1->{
					return Arrays.stream(split2).anyMatch(s2->Objects.equals(s1, s2));
				});
				if(clashValidation) {
					return true;
				}
			}
			return false;
		});
		
		
	}
}
