package  com.damianos.codingchallenge.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.damianos.codingchallenge.model.*;
import com.damianos.codingchallenge.service.NasaDataServiceInterface;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

@Controller
public class NasaDataController {

    private final Logger logger = LoggerFactory.getLogger(NasaDataController.class);

    @Autowired
    private NasaDataServiceInterface nasadataService;

    @RequestMapping(value = "report/{type}/generate", method = RequestMethod.GET)
    public String generatereport(@PathVariable("type") int type,Model model) {
        float successful = 0;
        float unsuccessful = 0;
        String buffer = "";

        List<PagesRank> top_10_unsuccessful_requests;
        List<HostsRank> top_10_requested_hosts;
        List<HostsTopPagesRank> top_5_pages_of_top_10_hosts;

        List<PagesRank> top_10_requested_pages  ;
        List<MalformedLine> MalformedLines_list  ;

        //model.addAttribute("pagerank", top_10_unsuccessful_requests);

        top_10_requested_pages = nasadataService.get_top_10_requested_pages();
        successful =  nasadataService.get_successful_requests_perc();
        unsuccessful =  nasadataService.get_unsuccessful_requests_perc();
        top_10_unsuccessful_requests =  nasadataService.get_top_10_unsuccessful_requests();
        top_10_requested_hosts = nasadataService.get_top_10_requested_hosts();
        top_5_pages_of_top_10_hosts = nasadataService.get_top_5_pages_of_top_10_hosts();


        buffer +=  " -----------  NASA LOG REPORT ----------------\n\n\n";
        Iterator it;
        switch (type) {
            case 0:
                buffer +=  "----- top_10_requested_pages  (page - number of requests ) ---------- \n\n";
                it=top_10_requested_pages.iterator();
                while(it.hasNext())
                {
                    PagesRank z=(PagesRank)it.next();
                    buffer += " " + z.getPage()   + " "+z.getHits() + "\n";
                }
                buffer += "----- successful requests percentage  ---------- \n\n";
                buffer += "success_reqs " + String.format("%.2f", successful) + "%  \n";
                buffer += "----- unsuccessful requests percentage  ---------- \n\n";
                buffer += "unsuccess_reqs " + String.format("%.2f", successful) + "%  \n";
                buffer += "----- top_10_unsuccessful_requests  ---------- \n\n";
                it=top_10_unsuccessful_requests.iterator();
                while(it.hasNext())
                {
                    PagesRank z=(PagesRank)it.next();
                    buffer += " " + z.getPage()   + " "+z.getHits() + "\n";
                }
                buffer +=  "----- The top 10 hosts making the most requests  ---------- \n\n";
                it=top_10_requested_hosts.iterator();
                while(it.hasNext())
                {
                    HostsRank z=(HostsRank)it.next();
                    buffer += " " + z.getHost()  + " "+z.getHits() + "\n";
                }
                break;
            case 1:
                buffer +=  "----- top_10_requested_pages  (page - number of requests ) ---------- \n\n";
                it=top_10_requested_pages.iterator();
                while(it.hasNext())
                {
                    PagesRank z=(PagesRank)it.next();
                    buffer += " " + z.getPage()   + " "+z.getHits() + "\n";
                }
                break;
            case 2:
                buffer += "----- successful requests percentage  ---------- \n\n";
                buffer += "success_reqs " + String.format("%.2f", successful) + "%  \n";
                break;
            case 3:
                buffer += "----- unsuccessful requests percentage  ---------- \n\n";
                buffer += "unsuccess_reqs " + String.format("%.2f", successful) + "%  \n";
                break;
            case 4:
                buffer += "----- top_10_unsuccessful_requests  ---------- \n\n";
                it=top_10_unsuccessful_requests.iterator();
                while(it.hasNext())
                {
                    PagesRank z=(PagesRank)it.next();
                    buffer += " " + z.getPage()   + " "+z.getHits() + "\n";
                }
                break;
            case 5:
                buffer +=  "----- The top 10 hosts making the most requests  ---------- \n\n";
                it=top_10_requested_hosts.iterator();
                while(it.hasNext())
                {
                    HostsRank z=(HostsRank)it.next();
                    buffer += " " + z.getHost()  + " "+z.getHits() + "\n";
                }
                break;
        }



        buffer +=  "\n ----- The top 5 pages from the top 10 hosts ---------- \n\n";
        it=top_5_pages_of_top_10_hosts.iterator();
        while(it.hasNext())
        {
            HostsTopPagesRank z=(HostsTopPagesRank)it.next();
            buffer += " " + z.getHost()  + " " + z.getPage() + " "+z.getHits() + "\n";
        }


        try{
            File file =new File("./data_process_report.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file,false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(buffer);
            bw.close();

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }

        return "report";
    }
}
