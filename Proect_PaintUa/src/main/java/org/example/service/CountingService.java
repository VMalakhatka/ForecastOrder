package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class CountingService {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    public void sale(ForecastTemplate forecastTemplate){
//        System.out.println(forecastTemplate);
//        forecastTemplate.getSetStockTTSet().forEach(setStockTT -> {
//            if(!setStockTT.getStockTipSaleSet().isEmpty())
//                saleStock(setStockTT,forecastTemplate);
//        });
//
//    }
//
//
//    public void rest(ForecastTemplate forecastTemplate){
//        Set<Long> stockSet=forecastTemplate.getSetStockTTSet().stream().map(SetStockTT::getIdStock).collect(Collectors.toSet());
//        forecastTemplate.getGoodsSet().forEach(good -> good.getRestSet().forEach(rest ->
//            {
//                if(rest.getKonKolich()!=0 && stockSet.contains(rest.getIdStock()))
//                    good.getForecast().setRestTT(good.getForecast().getRestTT()+rest.getKonKolich());
//            }));
//    }
//
//    public void dayWhenGoodsNotOnStock(ForecastTemplate forecastTemplate){
//        Set<Long> stockSet=forecastTemplate.getSetStockTTSet().stream().
//                filter(setStockTT -> setStockTT.getRole().equals(StockRole.PT) || setStockTT.getRole().equals(StockRole.OPT)). // возможны еще какие-либо роли для подсчета не продано
//                map(SetStockTT::getIdStock).collect(Collectors.toSet());
//        forecastTemplate.getGoodsSet().forEach(goods ->{
//            if (!goods.getGoodsMoveSet().isEmpty()){
//                stockSet.forEach(stock->{
//                    TypedQuery<GoodsMove> query = entityManager.createQuery(
//                            "SELECT e FROM GoodsMove e WHERE e.idStock = :stock ORDER BY e.data DESC", GoodsMove.class);
//                    query.setParameter("stock", stock);
//                    List<GoodsMove> moves = query.getResultList(); // Что будет если список будет огромный?
//                    if(!moves.isEmpty()){
//                        long nullDay= 0;
//                        LocalDateTime setNul=forecastTemplate.getStartAnalysis();
//                        double rest=goods.getRestSet().stream().filter(r->r.getIdStock()==stock).findFirst().orElseThrow().getKonKolich();
//                        double newRest;
//                        for(GoodsMove m : moves){
//                            m.setRest(rest);
//                            newRest=switch (m.getTypDocmPr()){
//                                case P -> rest+m.getQuantity();
//                                case R -> rest-m.getQuantity();
//                                case S -> rest;
//                            };
//                            if (m.getData().isAfter(setNul)) {
//                                if (rest <= 0 && newRest > 0.0)
//                                    nullDay += Math.abs(Duration.between(m.getData(), setNul).toDays());
//                                if (newRest <= 0 && rest > 0) setNul = m.getData();
//                            }
//                            rest=newRest;
//                        }
//                        if(rest<=0)
//                            nullDay+=Math.abs(Duration.between(forecastTemplate.getEndAnalysis(), setNul).toDays());
//                        goods.getForecast().setNotOnStock(goods.getForecast().getNotOnStock()+nullDay);
//                    }
//                });
//            }
//        });
//    }
//
//    private void saleStock(SetStockTT setStockTT, ForecastTemplate forecastTemplate) {
//        Set<StockTipSale> tips=setStockTT.getStockTipSaleSet();
//        if(Objects.requireNonNull(tips.stream().findFirst().orElse(null)).isEqual()) {
//            forecastTemplate.getGoodsSet().forEach(goods -> goods.getGoodsMoveSet().forEach(move -> {
//                if (move.getData().isAfter(forecastTemplate.getStartAnalysis()) &&
//                        move.getData().isBefore(forecastTemplate.getEndAnalysis())) {
//                    tips.forEach(t -> {
//                        boolean yes = false;
//                        if (t.getTypdocmPr().equals(move.getTypDocmPr())) {
//                            if (t.getOrgPredm().equals("ALL")) {
//                                yes = true;
//                            } else if (t.getOrgPredm().equals(move.getOrgPredm())) yes = true;
//                            if (yes) {
//                                yes = t.getVidDoc().equals(move.getVidDoc());
//                                if (t.getVidDoc().equals("ALL")) yes = true;
//                            }
//                        }
//                        if (yes)
//                            goods.getForecast().setSale(goods.getForecast().getSale() + move.getQuantity());
//                    });
//                }
//            }));
//        } else{
//            forecastTemplate.getGoodsSet().forEach(goods -> goods.getGoodsMoveSet().forEach(move->{
//                if(move.getData().isAfter(forecastTemplate.getStartAnalysis()) &&
//                        move.getData().isBefore(forecastTemplate.getEndAnalysis())) {
//                    AtomicBoolean no = new AtomicBoolean(false);
//                    AtomicBoolean yes = new AtomicBoolean(false);
//                    tips.forEach(t -> {
//                        if (!no.get()) {
//                            if (t.getTypdocmPr().equals(move.getTypDocmPr())) {
//                                yes.set(true);
//                                if (t.getOrgPredm().equals("ALL")) {
//                                    no.set(true);
//                                } else if (t.getOrgPredm().equals(move.getOrgPredm())) no.set(true);
//                                if (no.get()) {
//                                    no.set(t.getVidDoc().equals(move.getVidDoc()));
//                                    if (t.getVidDoc().equals("ALL")) no.set(true);
//                                }
//                            }
//                        }
//                    });
//                    if (yes.get() && !no.get())
//                        goods.getForecast().setSale(goods.getForecast().getSale() + move.getQuantity());
//                }
//            }));
//        }
//    }

}
