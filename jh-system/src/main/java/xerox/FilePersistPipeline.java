package xerox;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4.
 */
public class FilePersistPipeline implements Pipeline {


    private BufferedWriter bw;
    private String fieldSeparator;
    private String keyValueSeparator;

    public FilePersistPipeline(String filePath,String fieldSeparator,String keyValueSeparator) throws IOException {
        bw = new BufferedWriter(new FileWriter(filePath, true));
        this.fieldSeparator = fieldSeparator;
        this.keyValueSeparator = keyValueSeparator;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            if (!resultItems.isSkip()) {
                for (Map.Entry<String, Object> extraEntry : resultItems.getRequest().getExtras().entrySet()) {
                    if (!"statusCode".equals(extraEntry.getKey()) && !"morePagesList".equals(extraEntry.getKey())) {
                        bw.write(extraEntry.getKey() + keyValueSeparator + extraEntry.getValue() + fieldSeparator);
                    }
                }
                for (Map.Entry<String, Object> detailEntry : resultItems.getAll().entrySet()) {
                    if (!"statusCode".equals(detailEntry.getKey()) && !"morePagesList".equals(detailEntry.getKey())) {
                        bw.write(detailEntry.getKey() + keyValueSeparator + detailEntry.getValue() + fieldSeparator);
                    }
                }
                bw.write("\n\r");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (bw != null) {
            bw.close();
        }
    }
}
