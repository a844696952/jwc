package com.yice.edu.cn.common.pojo.jy.journal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewestJournal {
    private List<Journal> teachers;
    private List<Journal> students;

}
