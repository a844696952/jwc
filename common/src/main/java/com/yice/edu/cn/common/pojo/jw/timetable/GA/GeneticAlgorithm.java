package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
* @ClassName: GeneticAlgorithm  
* @Description: 遗传算法对象  
* @author xuchang  
* @date 2018年12月26日
 */
public class GeneticAlgorithm {
	private int populationSize;

	//突变率 0-1
	private double mutationRate;
	
	//突变比例
	private double mutationSelectRate;

	//交叉率
	private double crossoverRate;

	//优良数量
	private int elitismCount;
	
	private final int C=3500;
	
	

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate,double mutationSelectRate, int elitismCount) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.mutationSelectRate=mutationSelectRate;
	}

	/**
	 * 
	 * @Description: 初始化种群  
	 * @param 个体染色体长度
	 * @return    初始化生成的群体    
	 * @throws
	 */
	public Population initPopulation(int chromosomeLengthX,int chromosomeLengthY) {
		// Initialize population
		Population population = new Population(this.populationSize, chromosomeLengthX,chromosomeLengthY);
		return population;
	}
	
	/**
	 * 
	 * @Description: 初始化种群  
	 * @param 个体染色体长度
	 * @return    初始化生成的群体    
	 * @throws
	 */
	public Population initPopulation(ClassScheduleBo classSchedule) {
		Population population = new Population(this.populationSize, classSchedule);
		return population;
	}
	

	/**
	 * 
	 * @Description: 每个个体计算适应度  
	 * @param individual 需要计算的个体
	 * @return    适应度   
	 * @throws
	 */
	public double calcFitness(Individual individual,ClassScheduleBo classSchedule) {
		if(individual==null) return -C;
		double fitness=0.0;
		int clash=individual.detectionClash(classSchedule)?-1:0;
		// 克隆一个ClassSchedule
		ClassScheduleBo threadClassSchedule = new ClassScheduleBo(classSchedule);
		//condition1->权重设为50:计算每门课间隔是否大于一天的的节次 true 1,false 0
		double courseIntervalFitness=calcCourseIntervalFitness(individual, threadClassSchedule);
		// TODO condition2
		// TODO condition3
		fitness=courseIntervalFitness+clash*C;
		individual.setFitness(fitness);
		return fitness;
	}
	
	public int calcCourseIntervalFitness(Individual individual,ClassScheduleBo classSchedule) {
		Map<Integer, TeachInfoSplit> teachingPlans = classSchedule.getTeachingPlans();
		Matrix chromosome = individual.getChromosome();
		int days = classSchedule.getDaysSlots();
		int weeks=classSchedule.getWeeks();
		int courseFitness=1;
		for(int i=0;i<chromosome.row;i++) {
			int currentWeek=1;
			for(int j=0;j<days*weeks;j++) {
				if(j%days==0&&j!=0) currentWeek++;
				int cross1=chromosome.M[j][i];
				if(cross1==0) {
					continue;
				}
				boolean notInterval=false;
				for(int k=days*(currentWeek-1);k<days*currentWeek;k++) {
					int cross2=chromosome.M[k][i];
					if(j!=k&&cross2!=0) {
						if(Objects.equals(teachingPlans.get(cross1).getJwCourseId(), teachingPlans.get(cross2).getJwCourseId())) {
							notInterval= true;
							break;
						}
					}
					
				}
				if(!notInterval) {
					courseFitness++;
				}
				
			}
			
		}
		
		return courseFitness;
	}

	/**
	 * 
	 * @Description: 计算整个群体的适应度  
	 * @param population    计算的群体   
	 * @throws
	 */
	public void evalPopulation(Population population,ClassScheduleBo classSchedule) {
		double populationFitness = 0;
		
		populationFitness=Arrays.stream(population.getIndividuals())
				.mapToDouble(in->calcFitness(in,classSchedule))
				.parallel().sum();
		
		population.setPopulationFitness(populationFitness);
	}

	/**
	 * 
	 * @Description: 检查结束条件1  
	 * @param 已经计算的代数
	 * @param 最大代数
	 * @return    true结束,false继续    
	 * @throws
	 */
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}
	
	/**
	 * 
	 * @Description: 检查结束条件2  
	 * @param population
	 * @return    true结束,false继续       
	 * @throws
	 */
	public boolean isTerminationConditionMet(Population population) {
		return population.getFittest(0).getFitness() == 1.0;
	}

	/**
	 * 交叉计算
	 * 
	 * @param population
	 *            需要选择交叉个体的群体
	 * @return 被选择的个体
	 */
	public Individual selectParent(Population population) {
		
		//轮盘
		double populationFitness = population.getPopulationFitness();
		if(populationFitness>0) {
			double rouletteWheelPosition = Math.random() * populationFitness;

			double spinWheel = 0;
			for (Individual individual : population.getIndividuals()) {
				spinWheel += individual.getFitness();
				if (spinWheel >= rouletteWheelPosition) {
					return individual;
				}
			}
		}

		// 获取适应度最高的
		return population.getFittest(0);
	}

	/**
	 * 群体进行交叉遗传
	 * 
	 * 
	 * @param population
	 *            需要交叉的群体
	 * @return 新的群体
	 */
	public Population crossoverPopulation(Population population) {
		
		Population newPopulation = new Population(population.size());
		population.sortByFit();
		IntStream.range(0, population.size()).parallel().forEach(ind->{
			
			//获取父个体
			Individual parent1 = population.getFittest(ind);
			if (this.crossoverRate > Math.random() && ind >= this.elitismCount) {
				//随机选取父体
				Individual parent2 = selectParent(population);
				if(parent1!=null) {
					//时间
					int i = parent1.getChromosome().column;
					//班级
					int j = parent1.getChromosome().row;
					int status=0;
					int rowRandom=(int)(j*Math.random());
					List<Integer> pos=new ArrayList<>();
					int colRandom=(int)(i*Math.random());
					pos.add(colRandom);
					int crossValueInit1=parent1.getChromosome().M[colRandom][rowRandom];
					int crossValueInit2=parent2.getChromosome().M[colRandom][rowRandom];
					int crossValue2=crossValueInit2;
					int pos0=-1;
					while(crossValue2!=crossValueInit1&&status<i) {
						
						for(int n=crossValue2==0&&pos0!=-1?(pos0+1):0;n<i;n++) {
							
							if(parent1.getChromosome().M[n][rowRandom]==crossValue2) {
								if(crossValue2==0) {
									pos0=n;
								}
								crossValue2=parent2.getChromosome().M[n][rowRandom];
								pos.add(n);
								break;
							};
							
						}
						status++;
					}
					for(Integer po:pos) {
						parent1.getChromosome().setM(po, rowRandom, parent2.getChromosome().M[po][rowRandom]);
						// TODO 解决冲突
					}
				}else {
					
					parent1=parent2;
				}
				newPopulation.setIndividual(ind, parent1);
			} else {
				newPopulation.setIndividual(ind, parent1);
			}
			
			
			
		});
		return newPopulation;
	}
	

	/**
	 * 群体突变
	 * 
	 * @param population
	 *            需要突变的群体
	 * @return 突变后的群体
	 */
	public Population mutatePopulation(Population population,ClassScheduleBo classSchedule) {
		Population newPopulation = new Population(this.populationSize);
		population.sortByFit();
		IntStream.range(0, population.size()).parallel().forEach(populationIndex->{
			//getFittest获取的降序适应度排序
			Individual individual = population.getFittest(populationIndex);
			if (populationIndex > this.elitismCount) {
					//不变异优良个体
				if (this.mutationRate > Math.random()) {
					if(individual!=null) {
						List<Integer> times = IntStream.range(0, individual.getChromosome().row).boxed().collect(Collectors.toList());
						int select=(int)(mutationSelectRate*times.size())+1;
						for(int i=0;i<select;i++) {
							Integer randomRow= times.remove((int)(times.size()*Math.random()));
//							Arrays.stream(individual.getChromosome().M).forEach(m->{
//								m[randomRow]=0;
//							});
							Individual.generateClassSequence(classSchedule, randomRow+1, individual);
						}
					}else {
						individual=population.getFittest(0);
						
					}
					
					
					
				}
			}

			newPopulation.setIndividual(populationIndex, individual);
			
		});

		return newPopulation;
	}
	
	
}
