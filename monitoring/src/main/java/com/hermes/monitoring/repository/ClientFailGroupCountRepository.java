package com.hermes.monitoring.repository;

import com.hermes.monitoring.cvivis.dto.api.ApiClientFailHourlyCountDto;
import com.hermes.monitoring.entity.ClientGroupFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientFailGroupCountRepository extends JpaRepository<ClientGroupFail,Long> {
//select date,hour,url,method,sum(hourly_count) from client_fail where url = '/robots.txt' and method = 'GET' group by (url,method,date,hour) order by date desc,hour desc;


    @Query(value = "select new com.hermes.monitoring.cvivis.dto.api.ApiClientFailHourlyCountDto(r.date,r.hour,sum(r.hourlyCount)) " +
            "from ClientGroupFail as r  where r.groupName = :routing " +
            "group by r.date,r.hour,r.groupName order by r.date asc , r.hour desc")
    List<ApiClientFailHourlyCountDto> findDateAndCountByUrlAndMethod(@Param("routing")String routingGroup);

    @Query(value = "SELECT today.status_code as statusCode, today.count as count, round(cast(sum(hourly_count) as numeric) / (to_date(max(today),'YYYY-MM-DD') - cast(to_date(min(date),'YYYY-MM-DD') as date)),1) as avg" +
            " from client_fail_group as a JOIN " +
            "(select date as today, status_code, sum(hourly_count) as count from client_fail_group as b where b.group_name = ?1 and to_date(date,'YYYY-MM-DD') = current_date group by b.status_code, date order by count desc limit 3) as today " +
            "ON a.status_code = today.status_code and a.date != today.today " +
            "where a.group_name = ?1 " +
            "group by (today.status_code, today.count) order by count desc" , nativeQuery = true)
    List<ClientFailGroupCountRepository.ApiClientFailRankingVo> findTodayAndAvgCount(String groupName);

    interface ApiClientFailRankingVo{
        Integer getStatusCode();
        Long getCount();
        Double getAvg();

    }

}


