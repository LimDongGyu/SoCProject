package koreatech.cse.domain.MineralSpring;

public class MineralSpring {


    private String springName;                    //약수터이름
    private String springAddress;                 //약수터주소
    private String fitness;                       //적합도
    private String fitness_explain;               //부적합이유
    private String department_number;             //관리부서 번호
    private String result;                        //추천 결과


    public String getSpringName() {
        return springName;
    }

    public void setSpringName(String springName) {
        this.springName = springName;
    }

    public String getSpringAddress() {
        return springAddress;
    }

    public void setSpringAddress(String springAddress) {
        this.springAddress = springAddress;
    }

    public String getFitness() {
        return fitness;
    }

    public void setFitness(String fitness) {
        this.fitness = fitness;
    }

    public String getFitness_explain() {
        return fitness_explain;
    }

    public void setFitness_explain(String fitness_explain) {
        this.fitness_explain = fitness_explain;
    }

    public String getDepartment_number() {
        return department_number;
    }

    public void setDepartment_number(String department_number) {
        this.department_number = department_number;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MineralSpring{" +
                "springName='" + springName + '\'' +
                ", springAddress='" + springAddress + '\'' +
                ", fitness='" + fitness + '\'' +
                ", fitness_explain='" + fitness_explain + '\'' +
                ", department_number='" + department_number + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
