package com.javaee.stockmarket.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.javaee.stockmarket.domain.Action;
import com.javaee.stockmarket.domain.Company;
import com.javaee.stockmarket.domain.Investor;
import com.javaee.stockmarket.repository.ActionRepository;
import com.javaee.stockmarket.repository.CompanyRepository;
import com.javaee.stockmarket.repository.InvestorRepository;
import com.javaee.stockmarket.repository.OrderRepository;
import com.javaee.stockmarket.services.ActionService;

@Component
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private ActionService actionService;
	private ActionRepository actionRepository;
	private CompanyRepository companyRepository;
	private OrderRepository orderRepository;
	private InvestorRepository investorRepository;
	
	public ApplicationBootstrap(
			CompanyRepository companyRepository, 
			ActionService actionService, 
			ActionRepository actionRepository,
			OrderRepository orderRepository,
			InvestorRepository investorRepository) {
		this.companyRepository = companyRepository;
		this.actionService = actionService;
		this.actionRepository = actionRepository;
		this.investorRepository = investorRepository;
		this.orderRepository = orderRepository;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (companyRepository.count() == 0L) {
			companyRepository.deleteAll();
			actionRepository.deleteAll();
			orderRepository.deleteAll();
			loadInvestor();
			loadCompanies();
		}
	}
	
	private void loadInvestor() {
		investorRepository.deleteAll();
		Investor investorLucas = new Investor();
		investorLucas.setName("Lucas de Sousa Medeiros");
		investorLucas.setEmail("lucasmedeiros1994@gmail.com");
		investorLucas.setCpf("123.456.789-00");
		investorRepository.save(investorLucas);
		
		Investor investorPedro = new Investor();
		investorPedro.setName("Pedro Matos");
		investorPedro.setEmail("lucas_moema19@hotmail.com");
		investorPedro.setCpf("123.456.789-11");
		investorRepository.save(investorPedro);
	}
	
	private void loadCompanies() {
        Company companyPetrobras = new Company();
        companyPetrobras.setName("PETR");
        companyPetrobras.setCnpj("12.004.136/0001-59");
        companyRepository.save(companyPetrobras);

        Company companyAmazon = new Company();
        companyAmazon.setName("AMAZ");
        companyAmazon.setCnpj("28.470.025/0001-78");
        companyAmazon = companyRepository.save(companyAmazon);
        
        Action loteAmazon1 = new Action();
        loteAmazon1.setCompany(companyAmazon);
        loteAmazon1.setPrice(10.00);
        loteAmazon1.setQuantity(2);
        actionService.createNewAction(loteAmazon1);
        
        Action loteAmazon2 = new Action();
        loteAmazon2.setCompany(companyAmazon);
        loteAmazon2.setPrice(11.00);
        loteAmazon2.setQuantity(2);
        actionService.createNewAction(loteAmazon2);
    }
}
