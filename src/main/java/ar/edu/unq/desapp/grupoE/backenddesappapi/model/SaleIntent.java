package ar.edu.unq.desapp.grupoE.backenddesappapi.model;

public class SaleIntent extends OperationIntent{

    public SaleIntent(String activeCrypto, int nominalAmount, int cryptoPrice, int operationAmount) {
        super(activeCrypto, nominalAmount, cryptoPrice, operationAmount);
    }

    @Override
    public String getShippingAddress(User user) {
        return user.getCvu();
    }
}
