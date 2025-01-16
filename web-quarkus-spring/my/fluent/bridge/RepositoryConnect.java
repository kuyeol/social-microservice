package fluent.bridge;

import fluent.object.Entity;
import fluent.object.YouEntity;
import fluent.object.dto.Model;
import fluent.repo.JpaRepository;
import java.util.Optional;

public class RepositoryConnect<T extends Model>
{

    private final JpaRepository<Entity, String> userRepository;

    private Model model;


    public RepositoryConnect(JpaRepository<Entity, String> userRepository,
                             Model model)
    {

        this.userRepository = userRepository;
        this.model          = model;
    }


    public Model save(Entity entity)
    {

        userRepository.findById(entity.getId());


        return new Model(entity);
    }





    public <T extends Model> T  find(String id)
    {

        Optional<Entity> entity;

    entity = userRepository.findById(id);

        return (T) new Model(entity.orElseThrow());

    }


    private Model toModel(Optional<Entity> entity)
    {

        this.model = new Model(entity.orElseThrow());

        return model;
    }



    public void update(YouEntity user)
    {

        userRepository.update( user);
    }


    public void delete(YouEntity user)
    {

        userRepository.delete(user);
    }


}
