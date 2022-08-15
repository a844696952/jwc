package com.yice.edu.cn.common.pojo.jw.timetable.GA;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 
* @ClassName: Population  
* @Description: 群体  
* @author xuchang  
* @date 2018年12月26日
 */
public class Population {
	private Individual[] population;
	private double populationFitness = -1;

	public Population(int populationSize) {
		this.population = new Individual[populationSize];
	}

	public Population(int populationSize,ClassScheduleBo classSchedule) {
		
		this.population = new Individual[populationSize];
		IntStream.range(0, populationSize).forEach(i->{
			//根据计划随机个体
			Individual individual = Individual.initIndividual(classSchedule);
			this.population[i] = individual;
		});
	}
	
	public Population(int populationSize, int chromosomeLengthY,int chromosomeLengthX) {
		this.population = new Individual[populationSize];

		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			//生成空基因组成染色体
			Individual individual = new Individual(chromosomeLengthY,chromosomeLengthX);
			this.population[individualCount] = individual;
		}
	}

	public Individual[] getIndividuals() {
		return this.population;
	}
	
	//测试查找适应度最高的或者最低，其他索引
	public Individual getFittest(int position) {
		
		return this.population[position];
	}
	
	public void sortByFit() {
		
		Arrays.sort(this.population, (o1,o2)->{
			if(o1==null&&o2==null) {
				return 0;
			}
			if(o1==null) {
				return 1;
			}
			if(o2==null){
				return -1;
			}
			return (int)(o2.getFitness()-o1.getFitness());
			
		});
		
	}

	public void setPopulationFitness(double fitness) {
		this.populationFitness = fitness;
	}
	public double getPopulationFitness() {
		return this.populationFitness;
	}

	public int size() {
		return this.population.length;
	}

	public Individual setIndividual(int position, Individual individual) {
		return population[position] = individual;
	}

	public Individual getIndividual(int position) {
		return population[position];
	}
}