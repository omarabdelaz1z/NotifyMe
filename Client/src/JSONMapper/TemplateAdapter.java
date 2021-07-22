package JSONMapper;

import com.google.gson.Gson;
import Entities.Template;

    public class TemplateAdapter implements Adapter<Template> {
        @Override
        public Template toEntity(String JSON) {
            try{
                return new Gson().fromJson(JSON, Template.class);

            }catch(Exception e){
                System.out.println("Couldn't parse Input");
                return null;
            }
        }

        @Override
        public String toJSON(Template entity) {
            try{
                return new Gson().toJson(entity);
            }catch(Exception e){
                System.out.println("Couldn't parse Input");
                return null;
            }
        }
}
