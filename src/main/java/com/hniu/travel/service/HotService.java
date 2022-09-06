package com.hniu.travel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hniu.travel.bean.PopularDestination;
import com.hniu.travel.bean.TravelAround;
import com.hniu.travel.bean.WheatherDetails;
import com.hniu.travel.mapper.AreasMapper;
import com.hniu.travel.mapper.HotMapper;
import com.hniu.travel.mapper.ProductMapper;
import com.hniu.travel.pojo.Areas;
import com.hniu.travel.pojo.Hot;
import com.hniu.travel.pojo.Product;
import com.hniu.travel.util.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HotService {
    @Autowired
    private HotMapper hotMapper;
    @Autowired
    private StringTools stringTools;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private AreasMapper areasMapper;
    /***
     * 查询热门景点前十的景点
     * flag = 1 代表热门景点 flag = 2 代表热门目的地
     * @return
     */
    public PopularDestination findHotByLevel(Integer flag){

        //1.找出热门景点中上架的景点名称
        QueryWrapper<Hot> productHotQueryWrapper = new QueryWrapper<>();
        productHotQueryWrapper.eq("display", flag);
        List<Hot> hotTitles = hotMapper.selectList(productHotQueryWrapper);
        List<Product> hotProductList=new ArrayList<>();
        //2.根据上架产品的id查询上架的详细信息
        for (Hot hotTitle : hotTitles) {
            Product product = hotMapper.findHotByHid(hotTitle.getHid());
            product.setProductName(stringTools.removeExcessText(product.getProductName()));
            hotProductList.add(product);
        }

       return  new PopularDestination(hotTitles,hotProductList);
    }

    /**
     * 查询周边热门景点
     * @param cityName
     * @return
     */
    public TravelAround getTravelAround(String cityName){
        PopularDestination popularDestination = findHotByLevel(2);
        TravelAround travelAround = new TravelAround();
        //1.标签区
        travelAround.setHotTitleList(popularDestination.getHotTitleList());
        //2.图片区
        //周边地区只差挨着所选地区的俩个省会以内的景点
        //2.1通过地区名字搜索该省份的景点
            //1）通过城市查找其省份
            String province = weatherService.getWeatherInfo(cityName).getLives().get(0).getProvince();
            //2)根据省名查询省内的景点
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("productName", cityName);
            //周边的景点数据
            List<Product> products = productMapper.selectList(queryWrapper);
            //3)如果该省景点不够就从隔壁俩个省找
            if (products.size()<6){
                Areas localProvince = areasMapper.selectOne(new QueryWrapper<Areas>().eq("cName", cityName));

                Areas aroundOne = areasMapper.selectById(localProvince.getCid() + 10000);
                QueryWrapper<Product> queryWrapper1 = new QueryWrapper<>();
                queryWrapper.like("productName", aroundOne.getCName());
                List<Product> products1 = productMapper.selectList(queryWrapper1);
                for (Product product : products1) {
                    if (products.size()<6){
                        products.add(product);
                    }else {
                        break;
                    }
                }

                Areas aroundTwo = areasMapper.selectById(localProvince.getCid() + 20000);
                QueryWrapper<Product> queryWrapper2 = new QueryWrapper<>();
                queryWrapper.like("productName", aroundTwo.getCName());
                List<Product> products2 = productMapper.selectList(queryWrapper2);
                for (Product product : products2) {
                    if (products.size()<6){
                        products.add(product);
                    }else {
                        break;
                    }
                }
            }

        //如果周边地区不够就从热门区里区热门目的地的备用数据
        List<Product> hotProductList = popularDestination.getHotProductList();
        for (Product product : hotProductList) {
            if (products.size()<6){
                products.add(product);
            }else {
                break;
            }
        }

        //把图片区加载到TravelAround中去
        for (Product product : products) {
            product.setProductName(stringTools.removeExcessText(product.getProductName()));
        }
        travelAround.setTravelAroundList(products);
        return travelAround;

    }

    /**
     * 查询右侧边栏的热门推荐景点
     * @return
     */
    public List<Product> findRightHot(){
        QueryWrapper<Hot> productHotQueryWrapper = new QueryWrapper<>();
        productHotQueryWrapper.eq("display", 6);
        List<Hot> hotTitles = hotMapper.selectList(productHotQueryWrapper);
        List<Product> rightHot=new ArrayList<>();
        for (Hot hotTitle : hotTitles) {
            Product product = hotMapper.findHotByHid(hotTitle.getHid());
            product.setProductName(stringTools.removeExcessText(product.getProductName()));
            rightHot.add(product);
        }
        return rightHot;
    }
}
