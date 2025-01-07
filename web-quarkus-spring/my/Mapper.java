public interface Mapper<Entity, Dto> {


    Entity toDto(Dto dto);

    Dto toEntity(Entity entity);


}
