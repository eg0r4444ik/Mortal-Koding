package converter.impl;

import converter.ObjectConverter;
import entities.Problem;
import entities.Submission;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverterImpl implements ObjectConverter {
    @Override
    public User getUser(String response) {
        String[] userInfo = response.split("\"");
        String handle = "", email = "";
        long rating = 0;

        for(int i = 0; i < userInfo.length; i++){
            String str = userInfo[i];
            if(str.equals("handle")){
                handle = userInfo[i+2];
            }else if(str.equals("email")){
                email = userInfo[i+2];
            }else if(str.equals("rating")){
                rating = Long.parseLong(deleteQuotes(userInfo[i+1]));
            }
        }
        return new User(rating, handle, email);
    }

    @Override
    public Problem getProblem(String response) {
        StringBuilder sb = new StringBuilder(response);
        sb.deleteCharAt(0);
        response = sb.toString();

        long contestId = 0;
        String index = "", name = "";
        Problem.Type type = Problem.Type.PROGRAMMING;
        long rating = 0;
        List<String> tags = new ArrayList<>();

        String[] problemInfo = response.split(",|:| ");

        for(int i = 0; i < problemInfo.length; i++) {
            String s = problemInfo[i];
            switch (deleteQuotes(s)) {
                case ("contestId"):
                    contestId = Long.parseLong(problemInfo[i+1]);
                    break;
                case ("index"):
                    index = deleteQuotes(problemInfo[i+1]);
                    break;
                case ("name"):
                    name = deleteQuotes(problemInfo[i+1]);
                    break;
                case ("type"):
                    type = Problem.Type.valueOf(deleteQuotes(problemInfo[i+1]));
                    break;
                case ("rating"):
                    rating = Long.parseLong(problemInfo[i+1]);
                    break;
                case ("tags"):
                    String[] strTags = deleteQuotes(problemInfo[i+1]).split(" ");
                    for (String str : strTags) {
                        tags.add(deleteQuotes(str));
                    }
                    break;
            }
        }

        return new Problem(contestId, index, name, type, rating, tags);
    }

    @Override
    public Submission getSubmission(String response) {
        StringBuilder sb = new StringBuilder(response);
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        response = sb.toString();

        long creationTimeSeconds = 0;
        Problem problem = null;
        String programmingLanguage = "";
        Submission.Verdict verdict = Submission.Verdict.FAILED;

        String[] submissionInfo = response.split(",|:");

        for(int i = 0; i < submissionInfo.length; i++) {
            String s = submissionInfo[i];
            switch (deleteQuotes(s)) {
                case ("creationTimeSeconds"):
                    creationTimeSeconds = Long.parseLong(submissionInfo[i+1]);
                    break;
                case ("programmingLanguage"):
                    programmingLanguage = deleteQuotes(submissionInfo[i+1]);
                    break;
                case ("verdict"):
                    verdict = Submission.Verdict.valueOf(deleteQuotes(submissionInfo[i+1]));
                    break;
                case ("problem"):
                    StringBuilder prob = new StringBuilder();
                    i++;
                    while(i < submissionInfo.length && !deleteQuotes(submissionInfo[i]).equals("author")){
                        prob.append(submissionInfo[i] + " ");
                        i++;
                    }
                    problem = getProblem(prob.toString());
            }
        }

        return new Submission(creationTimeSeconds, problem, programmingLanguage, verdict);
    }

    @Override
    public List<Problem> getProblems(String response) {
        String[] problems = response.split("}],\"problemStatistics\":")[0].split("\"problems\":.")[1].split("},");
        List<Problem> result = new ArrayList<>();
        for(String str : problems){
            result.add(getProblem(str));
        }

        return result;
    }

    @Override
    public List<Submission> getSubmissions(String response) {
        String[] submissions = response.split("\"result\":..")[1].split(".\"id\":");
        List<Submission> result = new ArrayList<>();
        for(String str : submissions){
            result.add(getSubmission(str));
        }

        return result;
    }

    private String deleteQuotes(String string){
        if(string.length() < 2){
            return string;
        }
        StringBuilder sb = new StringBuilder(string);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
