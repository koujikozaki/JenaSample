import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;


public class searchRDFfromEndpoint {

	static public void main(String[] args) throws FileNotFoundException{

		//クエリの作成
		String queryStr = "select * where{?s ?p ?o.}LIMIT 100";
        Query query = QueryFactory.create(queryStr);

        //クエリの実行
        try ( QueryExecution qexec = QueryExecutionFactory.sparqlService(
        		"http://lod.hozo.jp/repositories/kgc2019" //SPARQL Endpoint の指定
        		, query) ) {

            FileOutputStream out;
			out = new FileOutputStream("output/dbp-output.txt");

			ResultSet rs = qexec.execSelect();

	        // 結果の出力　※以下のどれか「１つ」を選ぶ（複数選ぶと，2つ目以降の結果が「空」になる）
	     	//ResultSetFormatter.out(System.out, rs, query);		//表形式で，標準出力に
	     	//ResultSetFormatter.out(out, rs, query); 			//表形式で，ファイルに
	     	ResultSetFormatter.outputAsCSV(System.out, rs);	//CSV形式で，標準出力に
	     	//ResultSetFormatter.outputAsCSV(out, rs);			//CSV形式で，ファイルに
        } catch (Exception e) {
            e.printStackTrace();
        }

	}
}
