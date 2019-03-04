package com.aiwu.service;

import com.aiwu.bean.House;
import com.aiwu.repository.HouseRepository;
import com.aiwu.utils.HiveTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HiveTool hiveTool;

    @Transactional
    public void InsertHosue(House house)
    {
        houseRepository.save(house);
    }

    @Transactional
    public void DeleteHouse(String id)
    {
        houseRepository.deleteById(id);
    }

    public List<House> findAllHouses() { return houseRepository.findAll(); }

    @Transactional
    public void loadData() {
        List<House> houses = hiveTool.findHouseList("select * from house");
        while(!houses.isEmpty()) {
            houseRepository.save(houses.remove(0));
        }
    }

    @Transactional
    public List<House> findByCity(String city)
    {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 10;
            }

            @Override
            public int getPageSize() {
                return 10;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                Sort sort = new Sort(Sort.Direction.DESC, "city");
                return sort;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
        List<House> houseList = new ArrayList<House>();

        try {
           Page<House> pages = houseRepository.findAllByCity(city,pageable);
            Iterator<House> it = pages.iterator();

            while (it.hasNext()) {
                houseList.add(((House) it.next()));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return houseList;

    }

    @Transactional
    public String getName(int id)
    {
        return houseRepository.findAllById(id).getName();
    }

    @Transactional
    public List<House> findbymoney(String city,int minmoney,int maxmoney)
    {
        List<House> initList = findByCity(city);
        List<House> LL = new ArrayList<House>();
        for(int i=0;i<initList.size();i++)
        {
            if(initList.get(i).getPrice()>=minmoney&&initList.get(i).getPrice()<=maxmoney)
                LL.add(initList.get(i));
        }
        return LL;
    }

    @Transactional
    public List<House> findbytype(String type,String city)
    {
        List<House> initList = findByCity(city);
        List<House> LL = new ArrayList<House>();
        for(int i=0;i<initList.size();i++)
        {
            if(initList.get(i).getType().equals(type))
                LL.add(initList.get(i));
        }
        return LL;
    }

    @Transactional
    public List<House> findbybed(int bed,String city)
    {
        List<House> initList = findByCity(city);
        List<House> LL = new ArrayList<House>();
        for(int i=0;i<initList.size();i++)
        {
            if(initList.get(i).getBed()==bed)
                LL.add(initList.get(i));
        }
        return LL;
    }


    @Transactional
    public List<House> choose(String city,String type,int guest,int bedroom,int bed,int toilet,int minprice,int maxprice)
    {
        List<House> ancity = findByCity(city);
        System.out.print("按城市搜索结果：");
        System.out.println(ancity.size());
        Pageable pageable = null;

        if(type!=null)
        {
            pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return 10;
                }

                @Override
                public int getPageSize() {
                    return 10;
                }

                @Override
                public long getOffset() {
                    return 0;
                }

                @Override
                public Sort getSort() {
                    Sort sort = new Sort(Sort.Direction.DESC, "city");
                    return sort;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            List<House> houseList = new ArrayList<House>();
            try {
                Page<House> pages = houseRepository.findAllByTypeAndCity(type,city,pageable);
                Iterator<House> it = pages.iterator();

                while (it.hasNext()) {
                    houseList.add(((House) it.next()));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            ancity = getsame(ancity,houseList);

            System.out.print("按类型搜索结果：");
            System.out.println(ancity.size());
        }
        if(guest!=-1)
        {
            pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return 10;
                }

                @Override
                public int getPageSize() {
                    return 10;
                }

                @Override
                public long getOffset() {
                    return 0;
                }

                @Override
                public Sort getSort() {
                    Sort sort = new Sort(Sort.Direction.DESC, "city");
                    return sort;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            List<House> houseList = new ArrayList<House>();
            try {
                Page<House> pages = houseRepository.findAllByGuestAndCity(guest,city,pageable);
                Iterator<House> it = pages.iterator();

                while (it.hasNext()) {
                    houseList.add(((House) it.next()));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            ancity = getsame(ancity,houseList);

            System.out.print("按客人搜索结果：");
            System.out.println(ancity.size());
        }
        if(bedroom!=-1)
        {
            pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return 10;
                }

                @Override
                public int getPageSize() {
                    return 10;
                }

                @Override
                public long getOffset() {
                    return 0;
                }

                @Override
                public Sort getSort() {
                    Sort sort = new Sort(Sort.Direction.DESC, "city");
                    return sort;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            List<House> houseList = new ArrayList<House>();
            try {
                Page<House> pages = houseRepository.findByCityAndRoom(city,bedroom,pageable);
                Iterator<House> it = pages.iterator();

                while (it.hasNext()) {
                    houseList.add(((House) it.next()));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            ancity = getsame(ancity,houseList);

            System.out.print("按卧室数搜索结果：");
            System.out.println(ancity.size());
        }
        if(bed!=-1)
        {
            pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return 10;
                }

                @Override
                public int getPageSize() {
                    return 10;
                }

                @Override
                public long getOffset() {
                    return 0;
                }

                @Override
                public Sort getSort() {
                    Sort sort = new Sort(Sort.Direction.DESC, "city");
                    return sort;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            List<House> houseList = new ArrayList<House>();
            try {
                Page<House> pages = houseRepository.findAllByBedAndCity(bed,city,pageable);
                Iterator<House> it = pages.iterator();

                while (it.hasNext()) {
                    houseList.add(((House) it.next()));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            ancity = getsame(ancity,houseList);

            System.out.print("按床位数搜索结果：");
            System.out.println(ancity.size());
        }
        if(toilet!=-1)
        {
            pageable = new Pageable() {
                @Override
                public int getPageNumber() {
                    return 10;
                }

                @Override
                public int getPageSize() {
                    return 10;
                }

                @Override
                public long getOffset() {
                    return 0;
                }

                @Override
                public Sort getSort() {
                    Sort sort = new Sort(Sort.Direction.DESC, "city");
                    return sort;
                }

                @Override
                public Pageable next() {
                    return null;
                }

                @Override
                public Pageable previousOrFirst() {
                    return null;
                }

                @Override
                public Pageable first() {
                    return null;
                }

                @Override
                public boolean hasPrevious() {
                    return false;
                }
            };
            List<House> houseList = new ArrayList<House>();
            try {
                Page<House> pages = houseRepository.findAllByToiletAndCity(toilet,city,pageable);
                Iterator<House> it = pages.iterator();

                while (it.hasNext()) {
                    houseList.add(((House) it.next()));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            ancity = getsame(ancity,houseList);

            System.out.print("按卫生间搜索结果：");
            System.out.println(ancity.size());
        }
        if(minprice!=-1||maxprice!=-1)
        {
            List<House> houselist = findbymoney(city,minprice,maxprice);
            ancity = getsame(ancity,houselist);

            System.out.print("按价格搜索结果：");
            System.out.println(ancity.size());
        }
        return  ancity;
    }

    public List<House> getsame(List<House> alist,List<House> blist)//alist为主要
    {
        List<House> finallist = new ArrayList<House>();
        for(int i=0;i<alist.size();i++)
        {
            boolean same = false;
            for (int j = 0; j < blist.size(); j++) {
                if (alist.get(i).getId() == blist.get(j).getId())
                {
                    same = true;
                    break;
                }
            }
            if(same == true)
                finallist.add(alist.get(i));
        }
        return finallist;


    }




}
