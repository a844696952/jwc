package com.yice.edu.cn.yed.feignClient.general.standard;

import com.yice.edu.cn.common.pojo.general.standard.Standard;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StandardFeignFallbackFactory implements FallbackFactory<StandardFeign> {
    @Override
    public StandardFeign create(Throwable throwable) {
        return new StandardFeign() {
            @Override
            public Standard findStandardById(String id) {
                System.out.println(throwable.getMessage());
                return null;
            }

            @Override
            public Standard saveStandard(Standard standard) {
                System.out.println(throwable.getMessage());
                return null;
            }

            @Override
            public List<Standard> findStandardListByCondition(Standard standard) {
                return null;
            }

            @Override
            public long findStandardCountByCondition(Standard standard) {
                return 0;
            }

            @Override
            public void updateStandard(Standard standard) {
                throw new RuntimeException(throwable);
            }

            @Override
            public void deleteStandard(String id) {
                throw new RuntimeException(throwable);
            }
        };
    }
}
