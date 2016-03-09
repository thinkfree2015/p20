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

    public FilePersistPipeline(String filePath) throws IOException {
        bw = new BufferedWriter(new FileWriter(filePath, true));
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            if (!resultItems.isSkip()) {
                for (Map.Entry<String, Object> extraEntry : resultItems.getRequest().getExtras().entrySet()) {
                    if (!"statusCode".equals(extraEntry.getKey())) {
                        bw.write(extraEntry.getKey() + ":\t" + extraEntry.getValue() + "\t");
                    }
                }
                for (Map.Entry<String, Object> detailEntry : resultItems.getAll().entrySet()) {
                    bw.write(detailEntry.getKey() + ":\t" + detailEntry.getValue() + "\t");
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
