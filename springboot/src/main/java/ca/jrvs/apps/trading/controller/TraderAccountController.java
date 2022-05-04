package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import ca.jrvs.apps.trading.service.TraderAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import java.sql.Date;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(value = "Trader", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/trader")
public class TraderAccountController {

    private TraderAccountService traderAccountService;

    @Autowired
    public TraderAccountController(TraderAccountService traderAccountService) {
        this.traderAccountService = traderAccountService;
    }

    @ApiOperation(value = "Create a trader and an account.", notes = "TraderId and AccountId are auto generated by database,"
            + "and they should be identical. Assume each trader has exact one accout.")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping(path = "/firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public TraderAccountView createTrader(@PathVariable String firstname,
                                          @PathVariable String lastname, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                  LocalDate dob, @PathVariable String country, @PathVariable String email) {
        try {
            Trader trader = new Trader();
            trader.setFirst_name(firstname);
            trader.setLast_name(lastname);
            trader.setDob(Date.valueOf(dob));
            trader.setEmail(email);
            trader.setCountry(country);
            return traderAccountService.createTraderAndAccount(trader);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }

    }

    @ApiOperation(value = "Delete a trader", notes = "Delete a trader IFF its account amount is 0 and no open positions."
            + " Also delete the associated account and securityOrder.")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Unable to delete user")})
    @DeleteMapping(path = "/traderId/{traderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrader(@PathVariable Integer traderId) {
        try {
            traderAccountService.deleteTraderById(traderId);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Deposit a fund", notes = "Deposit a fund to the account that associates with the given traderId")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "traderId is not found"),
            @ApiResponse(code = 400, message = "Unable to deposit due to user input")
    })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping(path = "/deposit/traderId/{traderId}/amount/{amount}")
    public Account depositFund(@PathVariable Integer traderId, @PathVariable Double amount) {
        try {
            return traderAccountService.deposit(traderId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @ApiOperation(value = "Withdraw a fund", notes = "Withdraw a fund from the account that associates with the given traderId")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "traderId is not found"),
            @ApiResponse(code = 400, message = "Unable to withdraw due to user input.")
    })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PutMapping(path = "/withdraw/traderId/{traderId}/amount/{amount}")
    public Account withdrawFund(@PathVariable Integer traderId, @PathVariable Double amount) {
        try {
            return traderAccountService.withdraw(traderId, amount);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}