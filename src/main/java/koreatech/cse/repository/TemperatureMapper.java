package koreatech.cse.repository;

import koreatech.cse.domain.rest.Temperature;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureMapper {
    @Insert("INSERT INTO WSC.TEMPERATURE (SENSOR_ID, TEMPERATURE, DATETIME, LOCATION) VALUES (#{sensorId}, #{temperature}, #{datetime}, #{location})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
    void insert(Temperature temperature);

    @Update("UPDATE WSC.TEMPERATURE SET TEMPERATURE = #{temperature}, DATETIME = #{datetime}, LOCATION = #{location} WHERE ID = #{id}")
    void update(Temperature temperature);

    @Select("SELECT * FROM WSC.TEMPERATURE WHERE ID = #{id}")
    Temperature findOne(@Param("id") int id);

    @Delete("DELETE FROM WSC.TEMPERATURE WHERE ID = #{id}")
    void delete(@Param("id") int id);

    @Select("SELECT * FROM WSC.TEMPERATURE WHERE SENSOR_ID = #{sensorId}")
    Temperature findOneBySensorId(@Param("sensorId") String sensorId);

    @Select("SELECT * FROM WSC.TEMPERATURE WHERE LOCATION LIKE CONCAT('%', #{location}, '%')")
    List<Temperature> findByLocation(@Param("location") String location);
}
